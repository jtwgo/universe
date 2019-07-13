#!/bin/bash
CUR_PATH=$(cd `dirname $0`; pwd);
TOP_DIR=${CUR_PATH}/../..;
echo $CUR_PATH

function build_register_center()
{
	cd $TOP_DIR/src/service-register-center && mvn -s ${TOP_DIR}/vars/settings.xml clean package -Dmaven.test.skip=true
	if [ $? -ne 0 ];then
		echo "compile service-register-center failed."
		exit -1;
	fi
	cp $TOP_DIR/src/service-register-center/target/service-register-center*.jar ${TOP_DIR}/output/jtw/repo/
	cd -
}
function build_unified_center()
{
	cd $TOP_DIR/src/microservice-unified-center && mvn -s ${TOP_DIR}/vars/settings.xml clean package -Dmaven.test.skip=true
	if [ $? -ne 0 ];then
		echo "compile microservice-unified-center failed."
		exit -1;
	fi
	cp $TOP_DIR/src/microservice-unified-center/target/microservice-unified-center*.jar ${TOP_DIR}/output/jtw/repo/
	cd -
}

function build_spring_ribbon()
{
	cd $TOP_DIR/src/service-secws && mvn -s ${TOP_DIR}/vars/settings.xml clean package -Dmaven.test.skip=true
	if [ $? -ne 0 ];then
		echo "compile service-secws failed."
		exit -1;
	fi
	cp $TOP_DIR/src/service-secws/target/service-secws*.jar ${TOP_DIR}/output/jtw/repo/
	cd -
}

main()
{
	[ -d ${TOP_DIR}/output ] && rm -rf ${TOP_DIR}/output
	mkdir -p ${TOP_DIR}/output && cp -rf $TOP_DIR/Deploy/jtw $TOP_DIR/output/
	build_register_center
	build_unified_center
	build_spring_ribbon
	cd $TOP_DIR/output/ && tar -zcvf jtw_1.0.tar.gz jtw && rm -rf jtw
	cd -
}
main


