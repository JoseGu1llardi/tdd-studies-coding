# TDD - Test-Driven Domain

Personal study on **TDD (Test-Driven Development)** in Java.

## Goal

Practice the **Red → Green → Refactor** cycle: first write a failing test, then
write the minimum code to make it pass, and finally refactor while keeping every
test green.

## Stack

- **Java 21**
- **Maven**
- **JUnit 5 (Jupiter)** — testing framework
- **Mockito** — mocks and stubs

## The problem (base requirements)

> A library tracks the books borrowed by a single member. Implement a class
> `MemberLoans` with these requirements:
>
> - `borrow(String isbn)`: the member borrows the book with the given ISBN.
> - `giveBack(String isbn)`: the member returns the book with the given ISBN.
> - `borrowedCount()`: returns how many books the member currently has.
> - A member can have at most **3** books at the same time.
> - A member cannot borrow the same book twice while they still have it.
> - A member cannot give back a book they don't have.
> - The ISBN cannot be null or blank.

## The case study: `MemberLoans`

Models the book-loan tracking of a library member.

Rules exercised through tests:

- A member starts with **0** borrowed books.
- Borrowing a book increases the counter; returning one decreases it.
- Limit of **3 books** borrowed at the same time.
- The **same book** cannot be borrowed twice simultaneously.
- A book that is not currently borrowed cannot be returned.
- A failed operation **does not change** the state (the counter stays consistent).
- The **ISBN** cannot be null, empty, or blank.

## Structure

```
src/
├── main/java/MemberLoans.java        # production code
└── test/java/MemberLoansTest.java    # tests (JUnit 5)
```

## How to run the tests

```bash
mvn test
```

## Study notes: tests and TDD

**Before writing code**, write the test list by passing each requirement through the
checklist: happy path, initial state, limits, duplicates, invalid inputs, and the
inverse operation. Then combine the rules: what happens to the limit *after* a return?

**The cycle:** 🔴 a failing test → 🟢 the minimum code → 🔵 refactor with everything
green. Never write behavior without a test.

**A strong test:** ask "what wrong implementation would pass this test?". Prefer two
elements over one, and avoid checking only `0` or the empty case.

**If a test passes on the first try:** break the code on purpose to confirm it is
actually able to fail.

**Exceptions:** invalid argument → `IllegalArgumentException`; valid argument but the
state does not allow the operation → `IllegalStateException`.

**Order inside a method:** validate the argument, then the state, and only then mutate
the data. An exception must not undo something that already happened.

**Concurrency:** recognize the check-then-act pattern (`size()` followed by `add`) and
be able to explain it.

**Tools:** `@BeforeEach`, `assertThrows` (which returns the thrown exception),
`@ParameterizedTest` with `@NullSource` and `@ValueSource`, and private helper methods.
