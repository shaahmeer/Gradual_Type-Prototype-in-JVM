# Gradual Type Prototype in JVM

This project is a small prototype interpreter implemented in Java to explore ideas from gradual typing in JVM-based object-oriented languages.

## Features
- Lexer
- Parser
- Abstract Syntax Tree (AST)
- Interpreter
- Runtime type checks
- Gradual type errors

## Goal
The goal of this prototype is to better understand how dynamic and static typing can coexist in the same language, and how runtime checks can preserve type safety.

## Example
```text
let x: Dynamic = 5
x = "hello"
print(x)



In this example:

- `x` is declared as **Dynamic**
- The variable first stores an integer
- Later it stores a string
- The runtime system ensures safe interaction with statically typed contexts

---

 Research Relevance

This prototype explores key research challenges in gradual typing systems:

- Interaction between **dynamic and static types**
- **Runtime casts** and safety checks
- Efficient runtime implementation
- Type soundness in gradually typed languages

These ideas relate to research in **Programming Languages and Type Systems**, particularly work on **efficient runtimes for gradual typing**.

---

 Future Work

Possible extensions include:

- Static type annotations
- Subtyping and inheritance
- JVM object model integration
- Optimized runtime casts
- Performance evaluation of gradual typing

---

 References

- Siek, J., & Taha, W. (2006). *Gradual Typing for Functional Languages*
- Siek, J., Vitousek, M., Cimini, M., Boyland, J. (2015). *Refined Criteria for Gradual Typing*
- Muehlboeck, F., Tate, R. (2017). *Sound Gradual Typing is Nominally Alive and Well (POPL)*

---

Author

**Shahmeer Nawab**

Master’s in Industrial Programming  
RTU MIREA – Russian Technological University

Research interests:
- Programming Languages
- Gradual Typing
- Type Systems
- Runtime Verification
