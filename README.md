# 🚇 SubwayTicketSystem 🎟️

A simple Java subway ticket system with **Hangzhou Metro Line 3 stations**, **fare calculation**, **payment**, and **ticket issuance**.  
Built to be **GUI-friendly** ✅ (Swing/JavaFX can call the service methods directly).

---

## 📁 Project Folder Structure

SubwayTicketSystem/
├─ src/
│ ├─ exceptions/
│ │ └─ InsufficientFundsException.java
│ ├─ main/
│ │ └─ SubwayTicketSystem.java
│ ├─ model/
│ │ ├─ Payment.java
│ │ ├─ Station.java
│ │ └─ Ticket.java
│ ├─ service/
│ │ ├─ PaymentService.java
│ │ └─ TicketService.java
│ ├─ ui/
│ │ └─ TicketPurchaseUI.java
│ └─ utils/
│ ├─ InputValidator.java
│ └─ NumberFormatter.java
├─ LICENSE
├─ README.md


---

## ✨ Features

- 🚉 Choose **Start** & **End** station (Line 3)
- 🧮 Auto fare calculation (based on number of stops)
- 💰 Insert coins/bills via `Payment`
- ✅ Validate payment & generate `Ticket`
- 🧾 Ticket includes **issue time** automatically
- 💵 Returns **change** if overpaid

---

## 🔧 Key Methods (UI-Friendly) 🖱️

### `Station`
- `getStationNames()` → for dropdown list
- `stopsBetween(start, end)` → stop distance
- `isValidStationName(name)` → validation

### `TicketService`
- `calculateFare(start, end)` → returns fare (int)

### `PaymentService`
- `createPayment(start, end)` → returns `Payment`
- `generateTicket(start, end, payment)` → returns `Ticket` / throws `InsufficientFundsException`
- `getChange(payment)` → change amount
- `resetPayment(payment)` → reset payment

---

