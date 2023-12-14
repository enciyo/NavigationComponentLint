package com.enciyo.navigationcomponentlint.annotator

import com.enciyo.navigationcomponentlint.NavGraphLintIntention
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement


class ActionAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        try {
            val text = element.text.trim()
            if (isThereError(text).not()) return
            holder.newSilentAnnotation(HighlightSeverity.ERROR)
                .newFix(NavGraphLintIntention(text))
                .registerFix()
                .create()
        } catch (e: Exception) {
            println("${e.message}")
        }
    }

    private fun isThereError(text: String) = isNavigation(text) && isThereAction(text)
    private fun isNavigation(text: String) = text.startsWith("<navigation") && text.endsWith("</navigation>")
    private fun isThereAction(text: String) = text.contains("<action")
}






