#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
JNICALL
Java_com_MamaDevalayam_Interwork_MyApplication_getSheetApiKey(JNIEnv *env, jobject) {
//    std::string mNativeKey = "AIzaSyA9Cjc0gdK_CYnXSq1P_z3kG1dWg27h7c0";

    // TODO old

//    std::string mNativeKey = "AIzaSyDQzTsnTRgYvCDfEUm1ac0rQgHZbiiB_ew";

    // TODO New

    std::string mNativeKey = "AIzaSyAor4O91Bve-uqKx7au3QBr2Gl9_2Vr2kg";


//    std::string mNativeKey = "AIzaSyAor4O91Bve-uqKx7au3QBr2Gl9_2Vr2kg";
    return env->NewStringUTF(mNativeKey.c_str());
}

//extern "C" JNIEXPORT jstring
//JNICALL
//Java_com_MamaDevalayam_Interwork_MyApplication_getSheetApiKey(JNIEnv *env, jobject){
//    std::string mNativeKey = "AIzaSyBMkCWoTqmo_qdjL675bfgP5zbh1zboKCk"; // test
//    return env->NewStringUTF(mNativeKey.c_str());
//}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_MamaDevalayam_Interwork_MyApplication_getSheetId(JNIEnv *env, jobject) {

    // TODO old
//    std::string mNativeKey = "1O-8LD2wcWDqBiKw9I3QDI0JuwWCVrenyN_IzVHVMd4E";

//TODO new

    std::string mNativeKey = "1ZjrKWAsBuB0-P9A3-TePPUa3_t1iQxvY_FrH79fc5eQ";


//    std::string mNativeKey = "1ZjrKWAsBuB0-P9A3-TePPUa3_t1iQxvY_FrH79fc5eQ";
    return env->NewStringUTF(mNativeKey.c_str());
}

//extern "C" JNIEXPORT jstring
//JNICALL
//Java_com_MamaDevalayam_Interwork_MyApplication_getSheetId(JNIEnv *env, jobject){
//    std::string mNativeKey = "1wGP8IHJGOCT-K0t2eXAh_lmaGYRHgmWmgAIrUJ4ASQo"; // test
//    return env->NewStringUTF(mNativeKey.c_str());
//}