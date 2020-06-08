package com.bagelly.mvvm.model.api

import java.lang.RuntimeException

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.api
 * @ClassName: ApiException
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午3:50
 */
class ApiException (var code :Int,override var message:String):RuntimeException()