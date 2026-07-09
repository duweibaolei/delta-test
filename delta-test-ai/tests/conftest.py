"""
pytest 全局配置
pytest Global Configuration
<p>
定义测试 fixtures，提供 FastAPI 测试客户端。
Defines test fixtures, provides FastAPI test client.
</p>

@author ByDWL
"""

import pytest
from httpx import ASGITransport, AsyncClient

from app.main import app


@pytest.fixture
async def client() -> AsyncClient:
    """
    异步 HTTP 测试客户端
    Async HTTP test client

    Returns:
        AsyncClient: 异步测试客户端 / Async test client
    """
    transport = ASGITransport(app=app)
    async with AsyncClient(transport=transport, base_url="http://test") as ac:
        yield ac
