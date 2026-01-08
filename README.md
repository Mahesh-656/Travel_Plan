✈️ Travel Plan Management Service

A Spring Boot microservice that allows users to create travel plans and confirm them by integrating with an external Flight Booking microservice.
The system focuses on real-world business workflows, secure access, and reliable cross-service coordination.

🧠 Key Highlights

JWT-based authentication & authorization

User-scoped data access (no cross-user leaks)

Travel plan lifecycle management

External Flight Booking microservice integration

Booking rollback on failure

Idempotent confirmation logic

Clean, minimal, business-driven APIs (no CRUD noise)

🏗️ Architecture Overview
Client (JWT)
   ↓
Travel Plan Service
   ↓ (REST)
Flight Booking Service


Travel Plan Service manages plans and workflow

Flight Booking Service handles actual flight reservations

Services communicate via REST (RestTemplate)

External booking IDs are stored for traceability

🔐 Security

JWT authentication using Spring Security

All APIs are user-scoped

Travel plans can only be accessed by their owners

Status transitions are validated server-side

📌 Travel Plan Lifecycle
DRAFT
  ↓ (CONFIRM)
BOOKING_IN_PROGRESS
  ↓ (Success)
CONFIRMED
  ↓ (Failure)
CANCELLED


Once CONFIRMED, a plan cannot be modified

Booking failures automatically cancel the plan

Confirmation is idempotent

📚 APIs
1️⃣ Create Travel Plan

POST /api/travel-plans

Creates a new travel plan in DRAFT status.

Request Body

{
  "destinationId": "uuid",
  "sourceLocation": "Bengaluru",
  "startDate": "2025-02-10",
  "endDate": "2025-02-15"
}


Response

{
  "id": "uuid",
  "status": "DRAFT"
}

2️⃣ Get My Travel Plans

GET /api/travel-plans

Returns all travel plans for the authenticated user.

3️⃣ Get Travel Plan by ID

GET /api/travel-plans/{id}

Validates ownership

Prevents unauthorized access

4️⃣ Update Travel Plan Status

PATCH /api/travel-plans/{id}/status

Core business API.

Request Body

{
  "status": "CONFIRMED"
}


Behavior

CONFIRMED → triggers flight booking

CANCELLED → cancels the plan

Prevents invalid transitions

Rolls back on booking failure

5️⃣ Get Booking Details

GET /api/travel-plans/{planId}/booking

Returns external booking reference after confirmation.

Response

{
  "id": "uuid",
  "provider": "FLIGHT",
  "externalBookingId": 12,
  "status": "CONFIRMED"
}

6️⃣ Authentication APIs

Handled via JWT (Login / Register)

Secures all business endpoints

Integrated with Spring Security

7️⃣ Destination / Place APIs

Used to fetch available destinations required for creating travel plans.

🔄 External Flight Booking Integration

On confirmation:

Travel plan status → BOOKING_IN_PROGRESS

Flight booking request sent to Flight Booking Service

Booking ID returned

External booking reference stored

Travel plan marked as CONFIRMED

If booking fails:

Plan automatically transitions to CANCELLED

📦 Data Models (Simplified)
TravelPlan

id

user

destination

sourceLocation

startDate

endDate

status

ExternalBookingReference

id

travelPlan

provider (FLIGHT)

externalBookingId

status

🧪 Failure Handling

Booking failures handled with try/catch

Database state remains consistent

No partial confirmations

Safe retries via idempotency

🚀 Tech Stack

Java 17

Spring Boot

Spring Security (JWT)

Spring Data JPA

PostgreSQL

REST (Microservice communication)

✅ Project Status

Completion: ~90%
Scope: Intentionally limited to flight booking
Design Goal: Realistic backend service, not CRUD demo

🧑‍💻 Author

Mahesh
Backend-focused Java Developer
Specialized in Spring Boot & Microservices
