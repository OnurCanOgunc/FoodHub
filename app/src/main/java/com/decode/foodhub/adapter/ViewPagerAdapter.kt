package com.decode.foodhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import coil.load
import com.decode.foodhub.R
import com.decode.foodhub.models.Meal
import dagger.hilt.android.qualifiers.ApplicationContext

class ViewPagerAdapter (private val imageUrlList: List<Meal>, @ApplicationContext val context: Context): PagerAdapter() {

    var onItemClick: ((Meal) -> Unit)? = null

    override fun getCount(): Int = imageUrlList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.adapter_viewpager,null)

        val imageView = view.findViewById<ImageView>(R.id.image)
        imageView.load(imageUrlList[position].strMealThumb) {
            crossfade(true)
            crossfade(500)
        }

        imageView.setOnClickListener {
            onItemClick?.invoke(imageUrlList[position])
        }

        container.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}