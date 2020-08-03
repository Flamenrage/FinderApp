package com.example.finderapp.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.finderapp.Fragments.ElectedFragment
import com.example.finderapp.Fragments.FinderFragment

class CommonAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FinderFragment()
            else -> ElectedFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Найти"
            else -> "Избранные репозитории"
        }
    }

    override fun getCount(): Int {
        return 2
    }
}