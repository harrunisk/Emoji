package com.vanniktech.emoji.material;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import androidx.annotation.CallSuper;
import androidx.annotation.DimenRes;
import androidx.annotation.Px;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.vanniktech.emoji.EmojiManager;

@SuppressWarnings("CPD-START") public class EmojiMaterialRadioButton extends MaterialRadioButton {
  private float emojiSize;

  public EmojiMaterialRadioButton(final Context context) {
    this(context, null);
  }

  public EmojiMaterialRadioButton(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    if (!isInEditMode()) {
      EmojiManager.getInstance().verifyInstalled();
    }

    final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
    final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;

    if (attrs == null) {
      emojiSize = defaultEmojiSize;
    } else {
      final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiMaterialRadioButton);

      try {
        emojiSize = a.getDimension(R.styleable.EmojiMaterialRadioButton_emojiSize, defaultEmojiSize);
      } finally {
        a.recycle();
      }
    }

    setText(getText());
  }

  @Override @CallSuper public void setText(final CharSequence rawText, final BufferType type) {
    final CharSequence text = rawText == null ? "" : rawText;
    final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
    final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
    final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;
    EmojiManager.getInstance().replaceWithImages(getContext(), spannableStringBuilder, emojiSize, defaultEmojiSize);
    super.setText(spannableStringBuilder, type);
  }

  /** sets the emoji size in pixels and automatically invalidates the text and renders it with the new size */
  public final void setEmojiSize(@Px final int pixels) {
    setEmojiSize(pixels, true);
  }

  /** sets the emoji size in pixels and automatically invalidates the text and renders it with the new size when {@code shouldInvalidate} is true */
  public final void setEmojiSize(@Px final int pixels, final boolean shouldInvalidate) {
    emojiSize = pixels;

    if (shouldInvalidate) {
      setText(getText());
    }
  }

  /** sets the emoji size in pixels with the provided resource and automatically invalidates the text and renders it with the new size */
  public final void setEmojiSizeRes(@DimenRes final int res) {
    setEmojiSizeRes(res, true);
  }

  /** sets the emoji size in pixels with the provided resource and invalidates the text and renders it with the new size when {@code shouldInvalidate} is true */
  public final void setEmojiSizeRes(@DimenRes final int res, final boolean shouldInvalidate) {
    setEmojiSize(getResources().getDimensionPixelSize(res), shouldInvalidate);
  }
}