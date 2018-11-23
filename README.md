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

It will save the results in `output/` directory as CSV file.

## Observations

This algorithm seems to need very high number of maximum iterations for higher number of sequences.
Running it many times with low maximum number of iterations usually peaks quite fast and never improves
despite not reaching the best sequence. 

Using high number of iterations it usually finds the best solution in one of the first runs.

[Results for comparison](https://github.com/borkob/git_labs/blob/master/results-2016/2016-labs-skew.txt)
