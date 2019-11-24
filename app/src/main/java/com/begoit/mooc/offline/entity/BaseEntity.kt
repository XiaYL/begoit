package com.begoit.mooc.offline.entity

/**
 * @Description:服务器响应通用外层字段
 * @Author:gxj
 * @Time 2019/3/13
 */

open class BaseEntity {
    var status: Int = 0//1为正常返回
    var msg: String? = null//正常提示
    var error: String? = null//错误提示
}
