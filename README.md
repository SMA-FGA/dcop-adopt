>## dcop-adopt

An implementation of the algorithm _`ADOPT`_[1] to solve _Distributed Constraints Optimization Problems(DCOPs)_ using the [`JADE`](http://jade.tilab.com/) framework.

>## how to exec

>### with docker

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

>## references
1. Modi, P., Shen, W.-M., Tambe, M., & Yokoo, M. (2005). [ADOPT: Asynchronous distributed constraint optimization with quality guarantees](http://teamcore.usc.edu/papers/2005/aij-modi.pdf). Artificial Intelligence, 161(1–2), 149–180.