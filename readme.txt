En cuanto a la solución se ha utilizado el patrón de diseño state en el coordinador, esto se podría realizar con varias condiciones,
pero por la claridad de esta opción y experimentar con otras soluciones me decanté por esta. En el diseño se especificara mayormente
esta solución. Es una cadena de Markov con dos estados, en la que el primer estado si recibimos un REQ mandamos directamente CONF y
activamos el segundo estado, mientras que el segundo estado con cada REQ añade el id origen a la cola y con unn REL sacamos al que
le toque y le manamos el CONF.

Para Ejecutar: ./Compilar.sh

Para borrar .class: ./DeleteClass.sh
