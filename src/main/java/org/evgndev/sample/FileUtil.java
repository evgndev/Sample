package org.evgndev.sample;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.*;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.*;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    private static final Log log = LogFactoryUtil.getLog(FileUtil.class.getName());

    /**
     * Simple method for download entry file.
     * Throws error to view: {@code template.error.download-file}
     *
     * @param request              must contain argument <b>param</b> as file name parameter
     * @param response             response
     * @param param                file name parameter
     * @param useDescriptionAsName for using file description like name
     */
    public static void downloadEntryFile(ActionRequest request,
                                         ActionResponse response,
                                         String param,
                                         boolean useDescriptionAsName) {

        long fileId = ParamUtil.getLong(request, param);
        downloadEntryFile(request, response, fileId, useDescriptionAsName);
    }

    /**
     * Simple method for download entry file.
     * Throws error to view: {@code template.error.download-file}
     *
     * @param request              must contain argument <b>param</b> as file name parameter
     * @param response             response
     * @param fileId               file name parameter
     * @param useDescriptionAsName for using file description like name
     */
    public static void downloadEntryFile(ActionRequest request,
                                         ActionResponse response,
                                         long fileId,
                                         boolean useDescriptionAsName) {

        if (fileId > 0) {
            try {
                FileEntry file = DLAppLocalServiceUtil
                        .getFileEntry(fileId);

                String filename = "file";
                if (useDescriptionAsName) {
                    filename = file.getDescription();
                } else {
                    // for name like
                    // e51821bd-b52d-423c-8a2f-f6e91afb1e5b_filename.xlsx
                    filename = file.getTitle().substring(
                            file.getTitle().indexOf("_") + 1);
                }

                FileUtil.setHeadersForDownload(
                        PortalUtil.getHttpServletRequest(request),
                        PortalUtil.getHttpServletResponse(response),
                        filename,
                        MimeTypesUtil.getContentType(file.getTitle()));

                ServletResponseUtil.write(
                        PortalUtil.getHttpServletResponse(response),
                        file.getContentStream(), 0);

            } catch (Exception e) {
                SessionErrors.add(request, "template.error.download-file");
                log.error("Cannot get template file with id " + fileId,
                        e);
            }
        } else {
            SessionErrors.add(request, "template.error.download-file");
            log.error("Wrong file id! Used fileId " + fileId);
        }
    }

    public static void setHeadersForDownload(
            HttpServletRequest request, HttpServletResponse response,
            String fileName, String contentType) {

        // LEP-2201

        if (Validator.isNotNull(contentType)) {
            response.setContentType(contentType);
        }

        response.setHeader(
                HttpHeaders.CACHE_CONTROL, HttpHeaders.CACHE_CONTROL_PRIVATE_VALUE);
        response.setHeader(
                HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);

        if (Validator.isNotNull(fileName)) {
            String contentDisposition =
                    "attachment; filename=\"" + fileName + "\"";

            // If necessary for non-ASCII characters, encode based on RFC 2184.
            // However, not all browsers support RFC 2184. See LEP-3127.

            boolean ascii = true;

            for (int i = 0; i < fileName.length(); i++) {
                if (!Validator.isAscii(fileName.charAt(i))) {
                    ascii = false;

                    break;
                }
            }

            if (!ascii) {
                if (BrowserSnifferUtil.isIe(request)) {
                    //ie 7, 8 cannot open(without saving) file with cyrillic, see comments on http://pr.2bgroup.ru/issues/5924
                    fileName = cutFileName(fileName, 25);
                    contentDisposition = "attachment; filename=\"" + HttpUtil.encodeURL(fileName, true) + "\"";
                } else {
                    contentDisposition = "attachment; filename*=UTF-8''" + HttpUtil.encodeURL(fileName, true);
                }
            }

            response.setHeader(
                    HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
        }
    }

    private static String cutFileName(String fileName, int max) {
        String nameWithoutExtension = Files.getNameWithoutExtension(fileName);
        if (nameWithoutExtension.length() > max) {
            String extension = Files.getFileExtension(fileName);
            fileName = nameWithoutExtension.substring(0, 25) + "." + extension;
        }
        return fileName;
    }


    public static JSONObject deleteTempFile(ActionRequest actionRequest, ActionResponse actionResponse, String folderName) throws Exception {
        //delete file from liferay tmp folder, before uploaded
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        long folderId = ParamUtil.getLong(actionRequest, "folderId");
        String fileName = ParamUtil.getString(actionRequest, "fileName");
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
        try {
            DLAppServiceUtil.deleteTempFileEntry(themeDisplay.getScopeGroupId(), folderId, fileName, folderName);
            jsonObject.put("deleted", Boolean.TRUE);
        } catch (Exception e) {
            String errorMessage = themeDisplay.translate("an-unexpected-error-occurred-while-deleting-the-file");
            jsonObject.put("deleted", Boolean.FALSE);
            jsonObject.put("errorMessage", errorMessage);
        }
        return jsonObject;
    }

    public static JSONObject uploadTempFile(ActionRequest actionRequest,
                                            ActionResponse actionResponse,
                                            String folderName) throws Exception {

        //upload file in liferay tmp folder
        UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
        String sourceFileName = uploadPortletRequest.getFileName("file");
        InputStream inputStream = null;

        try {
            inputStream = uploadPortletRequest.getFileAsStream("file");
            String contentType = uploadPortletRequest.getContentType("file");

            DLAppServiceUtil.addTempFileEntry(themeDisplay.getScopeGroupId(), folderId,
                    sourceFileName, folderName, inputStream, contentType);
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
            jsonObject.put("name", sourceFileName);
            jsonObject.put("title", sourceFileName);
            return jsonObject;
        } catch (Exception e) {
            UploadException uploadException = (UploadException) actionRequest.getAttribute(WebKeys.UPLOAD_EXCEPTION);
            if ((uploadException != null) && uploadException.isExceededSizeLimit()) {
                throw new FileSizeException(uploadException.getCause());
            } else {
                throw e;
            }
        } finally {
            StreamUtil.cleanUp(inputStream);
        }
    }

    public static Map<String, FileEntry> getTempFiles(ActionRequest request,
                                                      String folder,
                                                      List<String> errors) throws Exception {

        Map<String, FileEntry> files = new HashMap<String, FileEntry>();
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        String[] selectedFiles = request.getParameterValues("selectUploadedFileCheckbox");

        if (selectedFiles != null) {
            for (String fileName : selectedFiles) {
                try {
                    FileEntry tempFile = TempFileUtil.getTempFile(
                            themeDisplay.getScopeGroupId(),
                            themeDisplay.getUserId(),
                            fileName,
                            folder
                    );

                    if (tempFile != null && !Strings.isNullOrEmpty(tempFile.getTitle())) {
                        files.put(fileName, tempFile);
                    }
                } catch (Exception e) {
                    errors.add("messaging.error.cannotGetFile$" + fileName);
                    throw new Exception("Error, cannot get file " + fileName, e);
                }
            }
        }
        return files;
    }

    /**
     * Save file input stream to library
     */
    public static long addFileToLibrary(InputStream inputStream,
                                        int size,
                                        String fileName,
                                        String fileDescription,
                                        String entryName,
                                        String parentFolderName) throws Exception {

        long fileEntryId = 0;

        String description = StringPool.BLANK;
        String changeLog = StringPool.BLANK;

        ServiceContext serviceContext = new ServiceContext();

        serviceContext.setAddGroupPermissions(true);

        long userId = UserLocalServiceUtil.getDefaultUserId(PortalUtil
                .getDefaultCompanyId());
        long groupId = GroupLocalServiceUtil.getGroup(
                PortalUtil.getDefaultCompanyId(), "Guest").getGroupId();

        Folder parentFolder = getOrCreateFolder(description, serviceContext,
                userId, groupId, parentFolderName, 0);

        if (parentFolder != null) {
            String entryFolderName = entryName;

            Folder entryFolder = getOrCreateFolder(description, serviceContext,
                    userId, groupId, entryFolderName,
                    parentFolder.getFolderId());

            if (entryFolder != null) {
                setPermissionForEntryFolder(entryFolder);

                long folderId = entryFolder.getFolderId();

                String sourceFileName = fileName;
                String title = fileName;

                FileEntry fileEntry = null;
                try {
                    fileEntry = DLAppLocalServiceUtil.getFileEntry(groupId,
                            entryFolder.getFolderId(), fileName);
                } catch (NoSuchFileEntryException e) {
                    log.debug(e.getMessage());
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

                if (fileEntry != null) {
                    fileEntry = DLAppLocalServiceUtil.updateFileEntry(userId,
                            fileEntry.getFileEntryId(), sourceFileName,
                            ContentTypes.TEXT_PLAIN, title, fileDescription,
                            changeLog, true, inputStream, size, serviceContext);
                } else {
                    fileEntry = DLAppLocalServiceUtil.addFileEntry(userId,
                            groupId, folderId, sourceFileName,
                            ContentTypes.TEXT_PLAIN, title, fileDescription,
                            changeLog, inputStream, size, serviceContext);
                }

                setPermissionForFileEntry(fileEntry);

                fileEntryId = fileEntry.getFileEntryId();
            }
        }

        return fileEntryId;
    }

    private static Folder getOrCreateFolder(String description,
                                            ServiceContext serviceContext,
                                            long userId,
                                            long groupId,
                                            String folderName,
                                            long parentFolderId) throws Exception {

        Folder result = null;

        Folder folder = getFolder(groupId, folderName, parentFolderId);

        if (folder != null) {
            result = folder;
        } else {
            result = DLAppLocalServiceUtil.addFolder(userId, groupId, parentFolderId, folderName, description, serviceContext);
        }

        return result;
    }

    private static Folder getFolder(long groupId, String folderName, long parentFolderId) {

        Folder folder = null;
        try {
            folder = DLAppLocalServiceUtil.getFolder(groupId, parentFolderId, folderName);
        } catch (Exception e) {
            log.error(e);
        }
        return folder;
    }

    private static void setPermissionForEntryFolder(Folder entryFolder) throws Exception {

        Role userRole = RoleLocalServiceUtil.getRole(PortalUtil.getDefaultCompanyId(), "User");

        //"com.liferay.portlet.documentlibrary.model.DLFolder"
        setPermissionForResource(
                DLFolder.class.getName(),
                String.valueOf(entryFolder.getPrimaryKey()),
                userRole.getRoleId(),
                new String[]{ActionKeys.VIEW}
        );
    }

    private static void setPermissionForFileEntry(FileEntry fileEntry) throws Exception {
        Role userRole = RoleLocalServiceUtil.getRole(PortalUtil.getDefaultCompanyId(), "User");

        //"com.liferay.portlet.documentlibrary.model.DLFileEntry"
        setPermissionForResource(
                DLFileEntry.class.getName(),
                String.valueOf(fileEntry.getPrimaryKey()),
                userRole.getRoleId(),
                new String[]{ActionKeys.VIEW}
        );
    }

    private static void setPermissionForResource(String resourceName,
                                                 String resourcePrimKey,
                                                 long roleId,
                                                 String[] actionIds) throws Exception {

        long companyId = PortalUtil.getDefaultCompanyId();

        ResourcePermissionLocalServiceUtil.setResourcePermissions(
                companyId,
                resourceName,
                ResourceConstants.SCOPE_INDIVIDUAL,
                resourcePrimKey,
                roleId,
                actionIds);

    }
}
