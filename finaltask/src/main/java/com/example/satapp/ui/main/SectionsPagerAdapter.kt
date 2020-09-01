package com.example.satapp.ui.main

import android.content.Context
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.satapp.R

private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_4,
        R.string.tab_text_5,
        R.string.tab_text_6
)

@Suppress("DEPRECATION")
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, inArray: Array<String?>) : FragmentPagerAdapter(fm) {

    private var outtext = ""
    var t : Array<String?> = inArray

    override fun getItem(position: Int): Fragment {
        when(position)
        {
            4 ->  t[0]?.let { return VideoPager(it) }!!
            5 ->  t[4]?.let { return ImagePager(it) }!!
            else -> {
                when(position) {
                    0 -> outtext = t[1]!!
                    1 -> outtext = t[2]!!
                    2 -> outtext = t[3]!!
                    3 -> outtext = t[6]!! + "\n" + t[7] + "\n" + t[8]
                }
                return TextPager(outtext)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

}