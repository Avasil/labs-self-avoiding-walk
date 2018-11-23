# labs-self-avoiding-walk

[Install Scala and SBT](https://www.scala-lang.org/download/)

Then:

```
git clone https://github.com/Avasil/labs-self-avoiding-walk.git
cd labs-self-avoiding-walk

sbt test
```

## Running simulation in loop

Takes three parameters in order:
- length of the sequence
- max number of iterations
- time in seconds
e.g.

```
sbt run 67 20000 900
```