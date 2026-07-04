"""
统一响应体
Unified Response Body
<p>
与 Java 端 R&lt;T&gt; 完全对齐的统一响应格式，包含 code、message、data、timestamp。
Unified response format fully aligned with Java R&lt;T&gt;, containing code, message, data, timestamp.
</p>

@author DeltaTest
"""

import time
from typing import Generic, TypeVar

from pydantic import BaseModel, Field

T = TypeVar("T")


class R(BaseModel, Generic[T]):
    """
    统一响应体
    Unified Response Body
    <p>
    与 Java 后端 com.dwl.common.response.R&lt;T&gt; 完全对齐。
    Fully aligned with Java backend com.dwl.common.response.R&lt;T&gt;.
    </p>
    """

    """响应状态码 / Response status code"""
    code: int = Field(default=200, description="响应状态码 / Response status code", examples=[200])

    """响应消息 / Response message"""
    message: str = Field(default="success", description="响应消息 / Response message", examples=["success"])

    """响应数据 / Response data"""
    data: T | None = Field(default=None, description="响应数据 / Response data")

    """响应时间戳(毫秒) / Response timestamp (milliseconds)"""
    timestamp: int = Field(
        default_factory=lambda: int(time.time() * 1000),
        description="响应时间戳(毫秒) / Response timestamp (ms)",
        examples=[1719936000000],
    )

    @classmethod
    def success(cls, data: T = None) -> "R[T]":  # type: ignore[assignment]
        """
        成功响应
        Success response

        Args:
            data: 响应数据 / Response data

        Returns:
            R[T]: 成功响应体 / Success response body
        """
        return cls(code=200, message="success", data=data)

    @classmethod
    def error(cls, message: str = "操作失败 / Operation failed") -> "R[None]":
        """
        错误响应
        Error response

        Args:
            message: 错误消息 / Error message

        Returns:
            R[None]: 错误响应体 / Error response body
        """
        return cls(code=500, message=message, data=None)

    @classmethod
    def error_with_code(cls, code: int, message: str) -> "R[None]":
        """
        带错误码的错误响应
        Error response with error code

        Args:
            code: 错误码 / Error code
            message: 错误消息 / Error message

        Returns:
            R[None]: 错误响应体 / Error response body
        """
        return cls(code=code, message=message, data=None)
