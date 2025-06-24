package com.superai.core.parser;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author : XiaoBaiBai
 * @description : AI增强解析结果:包含原始内容、结构化信息、摘要、实体、关键词、图像分析和表格分析
 * @created : 2025/6/24
 **/
@Data
public class EnhancedContent {
    /**
     * 原始文本内容
     */
    private String rawContent;
    /**
     * 结构化内容
     */
    private StructuredContent structuredContent;

    /**
     * 摘要信息
     */
    private String summary;

    /**
     * 关键实体（人物、地点、组织等）
     */
    private Map<String, List<String>> entities;

    /**
     * 关键词及其权重
     */
    private Map<String, Double> keywords;

    /**
     * 图像分析结果（如果有）
     */
    private ImageAnalysis imageAnalysis;

    /**
     * 表格分析结果（如果有）
     */
    private TableAnalysis tableAnalysis;


    /**
     * 结构化内容
     */
    @Data
    public static class StructuredContent {
        /**
         * 段落列表
         */
        private List<ContentSection> sections;
    }

    /**
     * 段落
     */
    @Data
    public static class ContentSection {
        /**
         * 段落标题
         */
        private String title;

        /**
         * 段落内容
         */
        private String content;

        /**
         * 段落关键词
         */
        private List<String> keywords;
    }

    /**
     * 图像分析
     */
    @Data
    public static class ImageAnalysis {
        /**
         * 图像描述
         */
        private String description;

        /**
         * 检测到的目标
         */
        private List<String> detectedObjects;

        /**
         * 检测到的文本
         */
        private List<String> recognizedText;
    }

    /**
     * 表格分析
     */
    @Data
    public static class TableAnalysis {
        /**
         * 表格数据
         */
        private List<List<String>> tableData;

        /**
         * 表格摘要
         */
        private String tableSummary;

        /**
         * 表格列描述
         */
        private Map<String, String> columnDescriptions;
    }
}
