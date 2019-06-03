>## dcop-adopt

An implementation of the _ADOPT_ algorithm to solve _DECOPs_ using the [_JADE_](http://jade.tilab.com/) framework.

```
DCOP: Distributed Constraints Optimization Problem
```
```
ADOPT: Asynchronous Distribuited Constraint Optimization with Quality Guarantees (ADOPT)
```
```
JADE: Java Agent Development Framework
```
>## how to exec

>#### with docker

`
cd my/path/dcop-adopt
`

`
sudo docker run --name dcop -it -v $PWD:/code -p 1099:1099 --workdir /code maven:3.6.1-jdk-11 bash
`

`
mvn install
`

`
mvn exec:java -D exec.mainClass="jade.Boot" -D exec.args="-agents instantiator:instantiator.InstantiatorAgent(./DCOPJson/a.json)"
`

>## run tests

run all tests:
`
mvn test
`

run single test:
`
mvn test -D test=GraphTest#IsNodeSizeCorrect_SholdReturnNumberOfNodesInTheGraph test
`
