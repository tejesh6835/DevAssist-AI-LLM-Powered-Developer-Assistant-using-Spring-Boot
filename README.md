# DevAssist AI – LLM-Powered Developer Assistant

![Java](https://img.shields.io/badge/Java-21-blue)
![SpringBoot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![SpringAI](https://img.shields.io/badge/Spring%20AI-Claude-orange)
![Architecture](https://img.shields.io/badge/Architecture-AI%20Orchestrator-yellow)
![Redis](https://img.shields.io/badge/Cache-Redis-red)
![Resilience4j](https://img.shields.io/badge/Resilience-CircuitBreaker-lightgrey)
![RateLimiting](https://img.shields.io/badge/Rate%20Limit-Enabled-pink)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)
![License](https://img.shields.io/badge/License-MIT-green)

---

DevAssist AI is a backend application built using **Spring Boot + Spring AI**, designed to assist developers by leveraging **LLMs (Claude)** for real-time code understanding and improvement.

It provides capabilities like:
- Code explanation
- Bug detection
- Log analysis
- Code refactoring
- Test case generation
- Code fixing

---

# Architecture Diagram
<img width="5480" height="3392" alt="image" src="https://github.com/user-attachments/assets/029d8786-9e26-456e-86de-542458c3cceb" />

---

# 🧠 Key Highlights

- ✅ Unified AI API architecture (single endpoint for multiple use cases)
- ✅ Centralized AI orchestration layer
- ✅ Structured prompt engineering using reusable templates
- ✅ Robust response validation and retry handling
- ✅ Redis caching for performance optimization
- ✅ Rate limiting to prevent API abuse
- ✅ Circuit breaker + retry for external AI reliability
- ✅ Token/input size control to handle LLM constraints

---

# 🏗️ Architecture Overview

```
Client → Controller → Service → AI Orchestrator
↓
Prompt Builder
↓
AI Client (Spring AI)
↓
Response Validator + Retry
↓
JSON Parsing
↓
DB Logging + Response
```

---

# ⚙️ Tech Stack

- **Backend**: Spring Boot (Java 21)
- **AI Integration**: Spring AI + Claude (Anthropic)
- **Database**: PostgreSQL
- **Caching**: Redis
- **Resilience**: Resilience4j (Circuit Breaker, Retry)
- **Rate Limiting**: Redis-based
- **Build Tool**: Maven

---

# 📌 Features

## 1. AI Orchestration Layer
- Centralized handling of:
  - Prompt construction
  - LLM calls
  - Response validation
  - Retry handling
  - Logging

## 2. Prompt Builder
- Reusable prompt templates
- Ensures:
  - Structured JSON output
  - Controlled response size
  - Consistent AI behavior

## 3. Response Validation
- Handles:
  - Malformed JSON
  - Truncated responses
  - Markdown removal
- Auto-retry with corrected prompts

## 4. Redis Caching
- Avoids repeated LLM calls for same input
- Improves performance and reduces cost

## 5. Rate Limiting
- Prevents API abuse
- Ensures fair usage across users

## 6. Circuit Breaker & Retry
- Handles AI failures gracefully
- Prevents cascading failures

## 7. Token/Input Size Control
- Prevents exceeding LLM token limits
- Ensures stable and cost-efficient requests

---

# 🔌 API Endpoint

## POST `/api/ai/ask`

### Request Body

```json
{
  "type": "explain",
  "input": "public int add(int a,int b){return a+b;}"
}
```

# Sample Response

### Request
```json
{
  "status": "SUCCESS",
  "type": "CODE_EXPLAIN",
  "data": {
    "purpose": "Adds two integers and returns their sum.",
    "issues": [
      { "issue": "No handling for int overflow when a + b exceeds Integer.MAX_VALUE", "severity": "LOW" }
    ],
    "improvements": [
      "Use long or Math.addExact(a, b) to surface overflow",
      "Add a Javadoc describing the contract"
    ],
    "bestPractices": [
      "Keep the method pure and side-effect free (already true)",
      "Add a unit test covering boundary values"
    ],
    "verdict": "Correct and simple; safe for typical inputs."
  },
  "timestamp": "2026-06-29T12:00:00.123"
}
```

---

## ✅ 2. Supported Request Types
# Supported Request Types

| Type          | Description |
|--------------|------------|
| explain      | Code explanation |
| logs         | Log analysis |
| testcases    | Generate JUnit test cases |
| refactor     | Improve code quality |
| detect-bugs  | Identify bugs in code |
| code-fix     | Fix issues in code |

# 🔮 Future Enhancements

- Streaming responses from LLM
- Multi-model support (OpenAI, Gemini)
- API authentication (JWT)
- User-based usage tracking
- UI dashboard for AI requests
- Prompt versioning system

# 💡 Why This Project?

This project demonstrates:

- Real-world LLM integration in backend systems
- Handling unreliable AI responses in production
- Designing scalable AI-driven APIs
- Applying backend patterns (caching, rate limiting, resilience)
- Understanding of prompt engineering + system design

# 👨‍💻 Author

**Tejesh Ankem**

Backend Developer | Java | Spring Boot | Microservices | AWS
- LinkedIn: www.linkedin.com/in/tejesh-ankem-a3772a1a3
- GitHub: [https://github.com/tejesh6835](https://github.com/tejesh6835/ )
