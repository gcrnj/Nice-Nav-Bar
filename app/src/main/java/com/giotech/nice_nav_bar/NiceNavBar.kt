package com.giotech.nice_nav_bar

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener


class NiceNavBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NavigationView(context, attrs, defStyleAttr) {

    //====================ATTRIBUTES================
    private var niceNavAccentColor = ContextCompat.getColor(context, R.color.black)
    private var niceNavCheckedTextColor = ContextCompat.getColor(context, R.color.black)
    private var niceNavUncheckedTextColor = ContextCompat.getColor(context, R.color.black)
    //=======================END OF ATTRIBUTES========================

    private var lastMenu: NavigationMenuItemView? = null
        set(value) {
            field?.alpha = 1f
            value?.alpha = 0.5f

            // OLD
            //Set field default style
            val fieldAccent = field?.findViewById<View>(R.id.niceNavigationVAccent)
            val fieldTxtTitle = field?.findViewById<TextView>(R.id.niceNavigationTxtTitle)
            fieldAccent?.apply {
                visibility = View.GONE
            }
            fieldTxtTitle?.apply {
                setTypeface(null, Typeface.NORMAL)
                setTextColor(niceNavUncheckedTextColor)
            }


            // NEW
            // Set value new style
            val valueAccent = value?.findViewById<View>(R.id.niceNavigationVAccent)
            val valueTxtTitle = value?.findViewById<TextView>(R.id.niceNavigationTxtTitle)
            valueAccent?.apply {
                visibility = View.VISIBLE
                setBackgroundColor(niceNavAccentColor)
            }
            valueTxtTitle?.apply {
                setTypeface(null, Typeface.BOLD)
                setTextColor(niceNavCheckedTextColor)
            }

            field = value
        }

    init {
        // Retrieve custom attributes
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomNavView)
        typedArray.apply {
            val defaultColor = ContextCompat.getColor(context, R.color.black)
            niceNavAccentColor =
                getColor(
                    R.styleable.CustomNavView_nice_nav_accent_color, defaultColor
                )
            niceNavCheckedTextColor =
                getColor(R.styleable.CustomNavView_nice_nav_checked_text_color, defaultColor)
            niceNavUncheckedTextColor =
                getColor(
                    R.styleable.CustomNavView_nice_nav_unchecked_text_color,
                    defaultColor
                )

            recycle()
        }

    }

    override fun setNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
//         Do additional customization
        val newListener =
            OnNavigationItemSelectedListener { item ->
                val willCheck = listener?.onNavigationItemSelected(item) == true
                if (willCheck) lastMenu = findViewById(item.itemId)
                willCheck
            }

        super.setNavigationItemSelectedListener(newListener)
    }

    override fun inflateMenu(resId: Int) {
        super.inflateMenu(resId)
        // Customize the menu items layout
        setMenuItemsTextStyles(menu)
    }

    private fun setMenuItemsTextStyles(menu: Menu) {
        menu.forEach { menuItem ->
            menuItem.setActionView(R.layout.nice_navigation_menu_item_view)
            val txtTitle = menuItem.actionView?.findViewById<TextView>(R.id.niceNavigationTxtTitle)
            Log.d("OKOK", menuItem.toString())
            txtTitle?.apply {
                setTextColor(if (menuItem.isChecked) niceNavCheckedTextColor else niceNavUncheckedTextColor)
                text = menuItem.title
            }
            // Do additional customization for each menu item

            val subMenu = menuItem.subMenu
            if (menuItem.hasSubMenu() && subMenu != null) {
                setMenuItemsTextStyles(subMenu)
            } else {
                menuItem.title = null
                // no submenu
            }
        }
    }

    //=======CHANGE DEFAULT LAYOUT OF NICE NAV BAR=======

}