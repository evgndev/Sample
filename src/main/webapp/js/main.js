/**
 * Add namespace Id prefix.
 * Add id selector with portlet namespace of current portlet to param
 * @param parameter
 * @returns string # + portlet:namespace + parameter
 */
function nsId(parameter) {
    return "#" + getNamespace() + parameter;
}

function showMask(placeholderSelector) {
    AUI().use('aui-loading-mask', function (A) {
        var div = A.one(placeholderSelector);
        if (!!div) {
            div.html("<div class='loading-animation'></div>");
        }
    });
}


/**
 * @param portletName
 * @param plid
 */
function createResourceURL(portletName, plid) {
    return addMainParams(Liferay.PortletURL.createResourceURL(), portletName, plid);
}

/**
 * @param portletName
 * @param plid
 */
function createRenderURL(portletName, plid) {
    return addMainParams(Liferay.PortletURL.createRenderURL(), portletName, plid);
}

function addMainParams(renderUrl, portletName, plid) {
    renderUrl.setPortletId(portletName);
    renderUrl.setPlid(plid);
    return renderUrl;
}

/**
 * Add field value parameter to url
 *
 * @param url Liferay.PortletURL
 * @param paramName
 * @param namespace "portlet:namespace" if liferay field
 */
function setFieldParam(url, paramName, namespace) {
    var selector = '#';
    if (namespace) {
        selector += namespace;
    }
    selector += paramName;

    var value = $(selector).val();
    url.setParameter(paramName, value);
}

/**
 * store parameter value to url and to cookie
 *
 * @param renderUrl to set param:value
 * @param param to get value
 * @param isString use it with string value to replace 0 to empty string
 */
function store(renderUrl, param, isString) {
    var value = $(nsId(param)).val();
    if (!value) {
        value = 0;
        if (isString) {
            value = "";
        }
    }
    $.cookie(param, value);
    renderUrl.setParameter(param, value);
}

/**
 * load parameter value from cookie set to url
 *
 * @param renderUrl to set param:value
 * @param param to set value
 * @param isString use it with string value to replace 0 to empty string
 */
function load(renderUrl, param, isString) {
    var value = $.cookie(param);
    if (!value || value == 'null') {
        if (isString) {
            value = "";
        } else {
            value = 0;
        }
    }
    renderUrl.setParameter(param, value);
    $(nsId(param)).val(value);
}

/**
 * Clear parameter, set to url and save cookie default value
 *
 * @param renderUrl to set param:value
 * @param param to get value
 * @param isString use it with string value to replace 0 to empty string
 */
function clear(renderUrl, param, isString) {
    var value = 0;
    if (isString) {
        value = "";
    }
    renderUrl.setParameter(param, value);
    $(nsId(param)).val(value);
    $.cookie(param, null);
}

function include(placeholderSelector, renderUrl, cmd) {
    renderUrl.setParameter('cmd', cmd);
    includeJSP(renderUrl, placeholderSelector);
}

function includeJSP(url, placeholderId) {
    jQuery.ajax({
        type: "POST",
        url: url,
        cache: false,
        dataType: "html",
        success: function (data) {
            $(placeholderId).html(data);
        }
    });
}

/**
 * remove old options, exclude first
 */
function clearCombobox(someSelect) {
    var firstOption = $(someSelect).find("option:first-child");
    someSelect.empty().append(firstOption);
}