package com.example.ozelderstakip.domain.repository

import com.example.ozelderstakip.data.local.dao.TemplateDao
import com.example.ozelderstakip.data.local.entity.TemplateEntity
import kotlinx.coroutines.flow.Flow

class TemplateRepository(private val templateDao: TemplateDao) {
    fun getAllTemplates(): Flow<List<TemplateEntity>> = templateDao.getAllTemplates()
    suspend fun insertTemplate(template: TemplateEntity): Long = templateDao.insertTemplate(template)
    suspend fun updateTemplate(template: TemplateEntity) = templateDao.updateTemplate(template)
    suspend fun deleteTemplate(template: TemplateEntity) = templateDao.deleteTemplate(template)
}
