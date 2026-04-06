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
<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/6665b4b1-e61e-413a-a577-420b984cfd19" />

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
  "type": "explain",
  "input": "public int add(int a,int b){return a+b;}"
}
```

---

## ✅ 2. Supported Request Types
```md
# 📌 Supported Request Types

| Type          | Description |
|--------------|------------|
| explain      | Code explanation |
| logs         | Log analysis |
| testcases    | Generate JUnit test cases |
| refactor     | Improve code quality |
| detect-bugs  | Identify bugs in code |
| code-fix     | Fix issues in code |
```

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

- LinkedIn: www.linkedin.com/in/tejesh-ankem-a3772a1a3
- GitHub: [https://github.com/tejesh6835](https://github.com/tejesh6835/ )
