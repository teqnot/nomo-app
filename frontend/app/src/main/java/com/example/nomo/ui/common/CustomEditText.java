package com.example.nomo.ui.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.nomo.R;

public class CustomEditText extends AppCompatEditText {
    private int idleTextColor;
    private int activeTextColor;

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        idleTextColor = ContextCompat.getColor(getContext(), R.color.secondary_text_on_background);
        activeTextColor = ContextCompat.getColor(getContext(), R.color.secondary_text_on_hover);

        // Установка начального цвета подсказки
        setHintTextColor(idleTextColor);

        // Получаем и устанавливаем иконки
        tintCompoundDrawables(idleTextColor);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    setTextColor(idleTextColor);
                    tintCompoundDrawables(idleTextColor);
                } else {
                    setTextColor(activeTextColor);
                    setHintTextColor(activeTextColor);
                    tintCompoundDrawables(activeTextColor);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        this.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus || getText().length() > 0) {
                tintCompoundDrawables(activeTextColor);
            } else {
                tintCompoundDrawables(idleTextColor);
            }
        });
    }

    private void tintCompoundDrawables(int color) {
        Drawable left = getCompoundDrawables()[0];
        Drawable top = getCompoundDrawables()[1];
        Drawable right = getCompoundDrawables()[2];
        Drawable bottom = getCompoundDrawables()[3];

        // Сохраняем размеры и состояние
        int leftWidth = left != null ? left.getIntrinsicWidth() : 0;
        int leftHeight = left != null ? left.getIntrinsicHeight() : 0;

        for (int i = 0; i < getCompoundDrawables().length; i++) {
            Drawable d = getCompoundDrawables()[i];
            if (d != null) {
                d = DrawableCompat.wrap(d.mutate());
                DrawableCompat.setTint(d, color);
            }

            switch (i) {
                case 0: setCompoundDrawablesWithIntrinsicBounds(d, top, right, bottom); break;
                case 1: setCompoundDrawablesWithIntrinsicBounds(left, d, right, bottom); break;
                case 2: setCompoundDrawablesWithIntrinsicBounds(left, top, d, bottom); break;
                case 3: setCompoundDrawablesWithIntrinsicBounds(left, top, right, d); break;
            }
        }
    }
}