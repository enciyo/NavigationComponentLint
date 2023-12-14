package com.enciyo.navigationcomponentlint

import com.enciyo.navigationcomponentlint.model.Navigation
import com.enciyo.navigationcomponentlint.utils.createNav
import com.gitlab.mvysny.konsumexml.konsumeXml
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.XmlElementFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.xml.XmlFile
import com.intellij.util.castSafelyTo
import java.io.File


class NavGraphLintIntention(private val content: String) : IntentionAction {
    override fun getText(): String = INTENTION_TEXT
    override fun startInWriteAction(): Boolean = true
    override fun getFamilyName(): String = INTENTION_FAMILY
    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean = true
    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        val navigation = content.konsumeXml().child("navigation") { Navigation.xml(this) }
        val directions = navigation.fragment + navigation.dialog + navigation.activity
        val actions = navigation.action + directions.flatMap { it.action }

        val xml = createNav(navigation).render()
        file.castSafelyTo<XmlFile>()?.rootTag?.replace(
            XmlElementFactory.getInstance(project).createHTMLTagFromText(xml)
        )
        file?.let { CodeStyleManager.getInstance(project).reformat(it) }

        val navigator = File("${project.basePath}/navigation", "NavigatorDirection.kt")
        navigator.parentFile.mkdir()
        navigator.writer().use {
            actions.forEach { action ->
                val direction = directions.find { it.id.split("/").last() == action.destination.split("/").last() }
                val argument = direction?.name + "Arg"
                it.write(
                    """
                    fun androidx.navigation.NavController.navigate(arg: ${argument}){
                        navigate(R.id.${direction?.id?.split("/")?.last()},arg.toBundle())
                    }
                """.trimIndent()
                )
                it.write("\n")
            }
        }

    }


}

