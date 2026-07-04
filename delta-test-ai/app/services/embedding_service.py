﻿﻿﻿﻿﻿"""
语义匹配服务
Embedding & Semantic Matching Service
<p>
基于 Embedding 向量进行用例语义匹配，骨架阶段返回占位数据。
Performs semantic case matching based on Embedding vectors, returns placeholder data in skeleton phase.
</p>

@author DeltaTest
"""


class EmbeddingService:
    """
    语义匹配服务
    Embedding & Semantic Matching Service
    <p>
    骨架阶段返回 mock 数据，Phase 1 接入 sentence-transformers + Milvus。
    Returns mock data in skeleton phase, integrates sentence-transformers + Milvus in Phase 1.
    </p>
    """

    async def match(self, query: str, top_k: int = 5) -> list[dict]:
        """
        语义匹配
        Semantic matching

        Args:
            query: 查询文本 / Query text
            top_k: 返回前K个结果 / Return top K results

        Returns:
            list[dict]: 匹配结果列表 / List of matched results
        """
        return [
            {
                "case_id": f"mock-{i}",
                "case_name": f"[MOCK] 匹配用例 {i} / Matched case {i}",
                "similarity": round(1.0 - i * 0.1, 2),
                "query": query,
            }
            for i in range(min(top_k, 3))
        ]


# 全局服务实例 / Global service instance
_embedding_service: EmbeddingService | None = None


def get_embedding_service() -> EmbeddingService:
    """获取语义匹配服务单例 / Get embedding service singleton"""
    global _embedding_service
    if _embedding_service is None:
        _embedding_service = EmbeddingService()
    return _embedding_service
