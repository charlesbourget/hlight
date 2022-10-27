# Hlight 

Simple Kotlin/Native script to add coloring when tailing logs on linux.

## Example

```bash
tail -f logfile | hlight ".*ERROR.*" ".*INFO.*"
```
