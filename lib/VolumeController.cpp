#include <jni.h>
#include <stdio.h>
#include "VolumeController.h"
#include <mmdeviceapi.h>
#include <endpointvolume.h>

#define _A ATLASSERT
#define __C ATLENSURE_SUCCEEDED
#define __D ATLENSURE_THROW

JNIEXPORT jboolean JNICALL Java_VolumeController_muteOn(JNIEnv *env, jclass clazz)  {
	HRESULT hr=NULL;
    
	bool decibels = false;
    bool scalar = false;

    CoInitialize(NULL);

    IMMDeviceEnumerator *deviceEnumerator = NULL;
    hr = CoCreateInstance(__uuidof(MMDeviceEnumerator), NULL, CLSCTX_INPROC_SERVER,
                          __uuidof(IMMDeviceEnumerator), (LPVOID *) &deviceEnumerator);

    IMMDevice *defaultDevice = NULL;

    hr = deviceEnumerator->GetDefaultAudioEndpoint(eRender, eConsole, &defaultDevice);
    deviceEnumerator->Release();
    deviceEnumerator = NULL;

    IAudioEndpointVolume *endpointVolume = NULL;
    hr = defaultDevice->Activate(__uuidof(IAudioEndpointVolume), CLSCTX_INPROC_SERVER, NULL, (LPVOID *)&endpointVolume);

    defaultDevice->Release();
    defaultDevice = NULL;

	hr = endpointVolume->SetMute(TRUE, NULL);
    
    endpointVolume->Release();

    CoUninitialize();
	
    return TRUE;
}

JNIEXPORT jboolean JNICALL Java_VolumeController_muteOff(JNIEnv *env, jclass clazz)  {

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

	hr = endpointVolume->SetMute(FALSE, NULL);

    endpointVolume->Release();
    CoUninitialize();

    return TRUE;
}

JNIEXPORT jboolean JNICALL Java_VolumeController_changeVolume(JNIEnv *env, jclass clazz, jint newValue)  {
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
    hr = defaultDevice->Activate(__uuidof(IAudioEndpointVolume), CLSCTX_INPROC_SERVER, NULL, (LPVOID *)&endpointVolume);
    defaultDevice->Release();
    defaultDevice = NULL;

    float volume = 0;
    if (newValue > 0) volume = ((float) newValue) / 100;

    printf("Value of new volume %f", volume);
	hr = endpointVolume->SetMasterVolumeLevelScalar(volume, NULL);

    endpointVolume->Release();

    CoUninitialize();

    return TRUE;
}

JNIEXPORT jint JNICALL Java_VolumeController_getVolume(JNIEnv *env, jclass clazz)  {
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

    float currentVolume = 0.00;
    hr = endpointVolume->GetMasterVolumeLevelScalar(&currentVolume);

    printf("Valore: %f\n", currentVolume);
    endpointVolume->Release();

    CoUninitialize();

    return currentVolume * 100;
}

JNIEXPORT jboolean JNICALL Java_VolumeController_increaseBy10Percentage(JNIEnv *env, jclass clazz)  {
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

    float currentVolume = 0.0;
    hr = endpointVolume->GetMasterVolumeLevelScalar(&currentVolume);

    currentVolume = currentVolume + 0.10;

	hr = endpointVolume->SetMasterVolumeLevelScalar(currentVolume, NULL);

    endpointVolume->Release();

    CoUninitialize();

    return TRUE;
}

JNIEXPORT jboolean JNICALL Java_VolumeController_decreaseBy10Percentage(JNIEnv *env, jclass clazz)  {
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

    float currentVolume = 0.0;
    hr = endpointVolume->GetMasterVolumeLevelScalar(&currentVolume);

    currentVolume = currentVolume - 0.10;

	hr = endpointVolume->SetMasterVolumeLevelScalar(currentVolume, NULL);

    endpointVolume->Release();

    CoUninitialize();

    return TRUE;
}

JNIEXPORT jboolean JNICALL Java_VolumeController_increaseBy1Percentage(JNIEnv *env, jclass clazz)  {
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

    float currentVolume = 0.0;
    hr = endpointVolume->GetMasterVolumeLevelScalar(&currentVolume);

    currentVolume = currentVolume + 0.01;

	hr = endpointVolume->SetMasterVolumeLevelScalar(currentVolume, NULL);

    endpointVolume->Release();

    CoUninitialize();

    return TRUE;
}

JNIEXPORT jboolean JNICALL Java_VolumeController_decreaseBy1Percentage(JNIEnv *env, jclass clazz)  {
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

    float currentVolume = 0.0;
    hr = endpointVolume->GetMasterVolumeLevelScalar(&currentVolume);

    currentVolume = currentVolume - 0.01;

	hr = endpointVolume->SetMasterVolumeLevelScalar(currentVolume, NULL);

    endpointVolume->Release();

    CoUninitialize();

    return TRUE;
}