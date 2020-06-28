package com.bagelly.mvvm.ui.main.discovery

import android.content.Context
import android.widget.ImageView
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Banner
import com.bagelly.mvvm.util.core.ImageOptions
import com.bagelly.mvvm.util.core.loadImage
import com.youth.banner.loader.ImageLoader

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.discovery
 * @ClassName: BannerImageLoader
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/28 下午4:03
 */
class BannerImageLoader :ImageLoader(){
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        val imagePath=(path as? Banner)?.imagePath
        imageView?.loadImage(imagePath, ImageOptions().apply {
            placeholder= R.drawable.shape_bg_image_default
        })
    }
}
