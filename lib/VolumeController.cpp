#include <jni.h>
#include <stdio.h>
#include "VolumeController.h"
#include <mmdeviceapi.h>
#include <endpointvolume.h>

#define _A  ATLASSERT
#define __C ATLENSURE_SUCCEEDED
#define __D ATLENSURE_THROW

JNIEXPORT short JNICALL Java_VolumeController_muteOn(JNIEnv *env, jobject obj)  {
	HRESULT hr=NULL;
    
	bool decibels = false;
    bool scalar = false;

    CoInitialize(NULL);
    IMMDeviceEnumerator *deviceEnumerator = NULL;
    hr = CoCreateInstance(__uuidof(MMDeviceEnumerator), NULL, CLSCTX_INPROC_SERVER, 
                          __uuidof(IMMDeviceEnumerator), (LPVOID *)&deviceEnumerator);
    IMMDevice *defaultDevice = NULL;

    hr = deviceEnumerator->GetDefaultAudioEndpoint(eRender, eConsole, &defaultDevice);
    deviceEnumerator->Release();
    deviceEnumerator = NULL;

    IAudioEndpointVolume *endpointVolume = NULL;
    hr = defaultDevice->Activate(__uuidof(IAudioEndpointVolume), 
         CLSCTX_INPROC_SERVER, NULL, (LPVOID *)&endpointVolume);
    defaultDevice->Release();
    defaultDevice = NULL;

    // -------------------------
	hr = endpointVolume->SetMute(TRUE, NULL);
    
    endpointVolume->Release();

    CoUninitialize();
	
    return hr;
}

JNIEXPORT short JNICALL Java_VolumeController_muteOff(JNIEnv *env, jobject obj)  {
	HRESULT hr=NULL;

	bool decibels = false;
    bool scalar = false;

    CoInitialize(NULL);
    IMMDeviceEnumerator *deviceEnumerator = NULL;
    hr = CoCreateInstance(__uuidof(MMDeviceEnumerator), NULL, CLSCTX_INPROC_SERVER,
                          __uuidof(IMMDeviceEnumerator), (LPVOID *)&deviceEnumerator);
    IMMDevice *defaultDevice = NULL;

    hr = deviceEnumerator->GetDefaultAudioEndpoint(eRender, eConsole, &defaultDevice);
    deviceEnumerator->Release();
    deviceEnumerator = NULL;

    IAudioEndpointVolume *endpointVolume = NULL;
    hr = defaultDevice->Activate(__uuidof(IAudioEndpointVolume),
         CLSCTX_INPROC_SERVER, NULL, (LPVOID *)&endpointVolume);
    defaultDevice->Release();
    defaultDevice = NULL;

    // -------------------------
    float currentVolume = 0;
    endpointVolume->GetMasterVolumeLevel(&currentVolume);
    //printf("Current volume in dB is: %f\n", currentVolume);

    hr = endpointVolume->GetMasterVolumeLevelScalar(&currentVolume);
    //CString strCur=L"";
    //strCur.Format(L"%f",currentVolume);
    //AfxMessageBox(strCur);

    // printf("Current volume as a scalar is: %f\n", currentVolume);

	hr = endpointVolume->SetMute(FALSE, NULL);

    endpointVolume->Release();

    CoUninitialize();

    return hr;
}

JNIEXPORT short JNICALL Java_VolumeController_changeVolume(JNIEnv *env, jobject obj, jfloat value)  {
	HRESULT hr=NULL;

	bool decibels = false;
    bool scalar = false;

    CoInitialize(NULL);
    IMMDeviceEnumerator *deviceEnumerator = NULL;
    hr = CoCreateInstance(__uuidof(MMDeviceEnumerator), NULL, CLSCTX_INPROC_SERVER,
                          __uuidof(IMMDeviceEnumerator), (LPVOID *)&deviceEnumerator);
    IMMDevice *defaultDevice = NULL;

    hr = deviceEnumerator->GetDefaultAudioEndpoint(eRender, eConsole, &defaultDevice);
    deviceEnumerator->Release();
    deviceEnumerator = NULL;

    IAudioEndpointVolume *endpointVolume = NULL;
    hr = defaultDevice->Activate(__uuidof(IAudioEndpointVolume),
         CLSCTX_INPROC_SERVER, NULL, (LPVOID *)&endpointVolume);
    defaultDevice->Release();
    defaultDevice = NULL;

    // -------------------------
    float currentVolume = 0;
    endpointVolume->GetMasterVolumeLevel(&currentVolume);
    //printf("Current volume in dB is: %f\n", currentVolume);

    hr = endpointVolume->GetMasterVolumeLevelScalar(&currentVolume);
    //CString strCur=L"";
    //strCur.Format(L"%f",currentVolume);
    //AfxMessageBox(strCur);

    // printf("Current volume as a scalar is: %f\n", currentVolume);

    printf("%f",value);
	hr = endpointVolume->SetMasterVolumeLevelScalar(value, NULL);

    endpointVolume->Release();

    CoUninitialize();

    return hr;
}