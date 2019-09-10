LIB=lib

ARCHIMEDES=2.0.1-SNAPSHOT
CORENT=1.67.1

CLASSPATH=$LIB/corent-$CORENT.jar

CLASSPATH=$CLASSPATH:$LIB/rest-acf-0.0.1.jar

CLASSPATH=$CLASSPATH:target/archimedes-alexandrian-application-$ARCHIMEDES.jar
CLASSPATH=$CLASSPATH:$LIB/archimedes-core-1.0.jar
CLASSPATH=$CLASSPATH:$LIB/baccara-1.10.1.jar
CLASSPATH=$CLASSPATH:$LIB/commons-lang3-3.1.jar
CLASSPATH=$CLASSPATH:$LIB/corentx-1.46.1.jar
CLASSPATH=$CLASSPATH:$LIB/gengen-1.12.2.jar
CLASSPATH=$CLASSPATH:$LIB/log4j-1.2.13.jar


echo $CLASSPATH

java -Darchimedes.properties=conf/archimedes.properties -Darchimedes.Archimedes.debug=true -Darchimedes.gui.ComponentDiagramm.roundCoor=true -Darchimedes.gui.ComponentDiagramm.SmartPositioner.mode=LEFTTOP -Darchimedes.gui.ComponentDiagramm.PAGESPERCOLUMN=5  -Darchimedes.gui.ComponentDiagramm.PAGESPERROW=10 -Dcorent.base.StrUtil.suppress.html.note=true -Dcorent.djinn.DefaultEditorDjinnPanel.markup.first.field=true -Dcorent.djinn.ChainKeyAdapter.on.focus.doClick=true -Dcorent.djinn.CreateCycle.vk.f12=VK_F9 -Dcorent.gui.Splashscreen.image=img/logoRotierendJingJang.gif -cp $CLASSPATH archimedes.legacy.Archimedes -i conf/archimedes.ini
