// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.kotlin.idea.formatter

import com.intellij.openapi.util.NlsContexts
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import org.jetbrains.kotlin.idea.core.formatter.KotlinCodeStyleSettings

object KotlinObsoleteStyleGuide {

    const val CODE_STYLE_ID = "KOTLIN_OLD_DEFAULTS"
    const val CODE_STYLE_SETTING = "obsolete"
    val CODE_STYLE_TITLE: @NlsContexts.ListItem String
        get() = KotlinFormatterMinimalBundle.message("kotlin.obsolete.coding.conventions.title")

    fun apply(settings: CodeStyleSettings) {
        applyToKotlinCustomSettings(settings.kotlinCustomSettings)
        applyToCommonSettings(settings.kotlinCommonSettings)
    }

    @JvmStatic
    fun applyToKotlinCustomSettings(kotlinCustomSettings: KotlinCodeStyleSettings, modifyCodeStyle: Boolean = true) {
        kotlinCustomSettings.apply {
            if (modifyCodeStyle) {
                CODE_STYLE_DEFAULTS = CODE_STYLE_ID
            }

            CONTINUATION_INDENT_IN_PARAMETER_LISTS = true
            CONTINUATION_INDENT_IN_ARGUMENT_LISTS = true
            CONTINUATION_INDENT_FOR_EXPRESSION_BODIES = true
            CONTINUATION_INDENT_FOR_CHAINED_CALLS = true
            CONTINUATION_INDENT_IN_SUPERTYPE_LISTS = true
            CONTINUATION_INDENT_IN_IF_CONDITIONS = true
            CONTINUATION_INDENT_IN_ELVIS = true
            WRAP_EXPRESSION_BODY_FUNCTIONS = CodeStyleSettings.DO_NOT_WRAP
            IF_RPAREN_ON_NEW_LINE = false
        }
    }

    @JvmStatic
    fun applyToCommonSettings(commonSettings: CommonCodeStyleSettings, modifyCodeStyle: Boolean = true) {
        commonSettings.apply {
            CALL_PARAMETERS_WRAP = CodeStyleSettings.DO_NOT_WRAP
            CALL_PARAMETERS_LPAREN_ON_NEXT_LINE = false
            CALL_PARAMETERS_RPAREN_ON_NEXT_LINE = false

            METHOD_PARAMETERS_WRAP = CodeStyleSettings.DO_NOT_WRAP
            METHOD_PARAMETERS_LPAREN_ON_NEXT_LINE = false
            METHOD_PARAMETERS_RPAREN_ON_NEXT_LINE = false

            EXTENDS_LIST_WRAP = CodeStyleSettings.DO_NOT_WRAP
            METHOD_CALL_CHAIN_WRAP = CodeStyleSettings.DO_NOT_WRAP
            ASSIGNMENT_WRAP = CodeStyleSettings.DO_NOT_WRAP
        }

        if (modifyCodeStyle && commonSettings is KotlinCommonCodeStyleSettings) {
            commonSettings.CODE_STYLE_DEFAULTS = CODE_STYLE_ID
        }
    }
}