package com.intellect.commerce.util;

import static java.util.Locale.US;
import android.text.TextUtils;

/**
 * Utilities for dealing with Markdown files
 */
public class MarkdownUtils {

  private static final String[] MARKDOWN_EXTENSIONS = { ".md", ".mkdn",
          ".mdwn", ".mdown", ".markdown", ".mkd", ".mkdown", ".ron" };

  /**
   * Is the the given file name a Markdown file?
   *
   * @param name
   * @return true if the name has a markdown extension, false otherwise
   */
  public static boolean isMarkdown(String name) {
      if (TextUtils.isEmpty(name))
          return false;

      name = name.toLowerCase(US);
      for (String extension : MARKDOWN_EXTENSIONS)
          if (name.endsWith(extension))
              return true;

      return false;
  }
}
