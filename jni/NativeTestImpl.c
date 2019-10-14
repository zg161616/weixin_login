#include<sys/types.h>
#include<jni_md.h>
#include<jni.h>
#include<stdio.h>
#include<malloc.h>
#include<stdlib.h>
#include<string.h>
#include"com_cwc_test_NativeTest.h"
JNIEXPORT void JNICALL Java_com_cwc_test_NativeTest_hello(JNIEnv* env, jobject obj,jstring name) {
	char* zz = _JString2CStr(env,name);
	printf("%c", zz);
}

void printIntTypeSize() {
	printf("%lu",sizeof(float));
}


char* _JString2CStr(JNIEnv* env, jstring jstr) {
	char* rtn = NULL;
	jclass clsstring = env->FindClass("java/lang/String");
	jstring strencode = env->NewStringUTF("GB2312");
	jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray)(env)->CallObjectMethod(jstr, mid, strencode); // String .getByte("GB2312");
	jsize alen = env->GetArrayLength(barr);
	jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*)malloc(alen + 1); //"\0"
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	env->ReleaseByteArrayElements(barr, ba, 0);
	return rtn;
}


int main() {
	extern int a;
	int	b = 2;
	printf("%d",a + b);
	printIntTypeSize();
}



