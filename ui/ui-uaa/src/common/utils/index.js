/**
 * 获取地址栏参数值
 * @param name
 * @returns {null}
 * @constructor
 */
export function GetQueryString (name) {
  var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)')
  var r = window.location.search.substr(1).match(reg)
  var s = window.location.search
  var href = window.location.href
  var index = href.indexOf('?')
  if (s === '') {
    r = href.substr(index + 1).match(reg)
  }
  if (r != null) return unescape(r[2])
  return null
}

export function getMobileRegex () {
  var regex = '^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$'
  // var reg = new RegExp(regex)
  return regex
}

export function getAuthorityStringRegex () {
  var regex = '^AUTH_'
  // var reg = new RegExp(regex)
  return regex
}

export function getRoleStringRegex () {
  var regex = '^ROLE_'
  // var reg = new RegExp(regex)
  return regex
}
