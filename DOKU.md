# Projektbericht – PIC-Mikrocontroller Simulator

**Projektname:**       PIC16F8X Java-Simulator  
**Abgabedatum:**         
**Teammitglieder:**    

---

## 1. Projektbeschreibung

In diesem Projekt wurde ein Simulator für den Mikrocontroller PIC16F8X in Java entwickelt. Ziel war es, zentrale Funktionen wie Datenregister, Ports, Timer0, Interrupts und Program Counter zu simulieren. 
Das Projekt dient sowohl der praktischen Vertiefung der Architektur von Mikrocontrollern als auch der Programmstrukturierung in Java.

---

## 2. Projektstruktur

Das Programm besteht aus mehreren modularen Klassen, die jeweils bestimmte Funktionseinheiten des Mikrocontrollers abbilden:

| Klasse               | Funktion |
|----------------------|----------|
| `DataMemory`         | Speichert alle Register (W, PC, INTCON, etc.) und steuert das Interruptsystem |
| `Timer0`             | Simuliert einen 8-Bit Timer mit Prescaler und Overflow-Erkennung |
| `Port`               | Simuliert Ein-/Ausgabe über PORTA/B inkl. TRIS-Steuerung |
| `InstructionDecoder` | Dekodiert Maschinenbefehle und ruft die Ausführungsmethoden auf |
| `ProgramMemory`      | Speichert den geladenen Programmcode |
| `GUIsim`             | Darstellung von Registerzuständen, Ausführung, Steuerung per GUI |


---

## 3. Funktionale Umsetzung

Das Programm bietet folgende Kernfunktionen:

- Einzelne Ausführung (Step)
- Laufender Programmbetrieb (Run)
- Timer0-Simulation inkl. Prescaler und Overflow
- Externe Interrupts über RB0 und PORTB (RB4–RB7)
- Stacksimulation zur Rücksprungadresse
- GUI-Anzeige aller wichtigen Register (W, PC, STATUS, PORTA/B)
- Ladefunktion für Programme (aus `.hex` Datei oder Text)

---

## 4. Herausforderungen und Lösungen

Während der Entwicklung traten verschiedene technische und konzeptionelle Schwierigkeiten auf:

- **Modularisierung:** Anfangs war unklar, welche Funktionen in welche Klassen gehören sollten. Durch Refactoring und Trennung von Port, Timer und Memory konnte Klarheit geschaffen werden.
- **Interrupt-Logik:** Die korrekte Priorisierung und Ausführung von Interrupts war komplex. Es wurde eine zentrale `checkAndHandleInterrupt()` Methode erstellt, die in jedem Zyklus geprüft wird.
- **Bitmasken und Shift-Logik:** Die gezielte Manipulation von Registerbits (z.B. RBIF, INTF) erforderte präzises Arbeiten mit Bit-Operationen, was anfangs fehleranfällig war.

---

## 5. Persönliches Fazit


> Zu Beginn hatte ich nur wenig Verständnis dafür, wie Interrupts oder Ports in einem Mikrocontroller auf unterster Ebene funktionieren. Durch dieses Projekt habe ich nicht nur gelernt, wie man diese in Java simulieren kann, sondern auch, wie man größere Programme modular strukturiert. Besonders stolz bin ich darauf, die Stack-Logik inklusive Rücksprungadresse korrekt implementiert zu haben.

> Die Zusammenarbeit im Team verlief insgesamt positiv, auch wenn wir anfangs unterschiedliche Vorstellungen zur Code-Struktur hatten. Durch regelmäßige Absprachen konnten wir eine gemeinsame Lösung finden. Ich habe gelernt, klarer zu kommunizieren und technische Entscheidungen besser zu begründen.

---

## 6. Anhang

- GUI screenshot
- Textfile...

---

## 7. Quellen / Tools

- Eclipse IDE
- JavaFX / Swing
- GitHub Repository: 
