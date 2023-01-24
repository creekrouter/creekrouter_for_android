package com.hi.demohelper.load;

import static com.hi.demohelper.load.PluginHelper.PLUGIN_MAIL;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ChangedPackages;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.VersionedPackage;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.UserHandle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

public class PluginPkgManager extends PackageManager {
    private PackageManager packageManager;
    private PackageInfo packageInfo;
    public PluginPkgManager(PackageManager packageManager){
        this.packageManager = packageManager;
        String path = PluginHelper.dirMap.get(PLUGIN_MAIL);
        packageInfo = this.packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
    }
    @Override
    public PackageInfo getPackageInfo(@NonNull String packageName, int flags) throws NameNotFoundException {
        return packageInfo;
    }

    @Override
    public PackageInfo getPackageInfo(@NonNull VersionedPackage versionedPackage, int flags) throws NameNotFoundException {
        return packageInfo;
    }

    @Override
    public String[] currentToCanonicalPackageNames(@NonNull String[] packageNames) {
        return packageManager.currentToCanonicalPackageNames(packageNames);
    }

    @Override
    public String[] canonicalToCurrentPackageNames(@NonNull String[] packageNames) {
        return packageManager.canonicalToCurrentPackageNames(packageNames);
    }

    @Nullable
    @Override
    public Intent getLaunchIntentForPackage(@NonNull String packageName) {
        return packageManager.getLaunchIntentForPackage(packageName);
    }

    @Nullable
    @Override
    public Intent getLeanbackLaunchIntentForPackage(@NonNull String packageName) {
        return packageManager.getLeanbackLaunchIntentForPackage(packageName);
    }

    @Override
    public int[] getPackageGids(@NonNull String packageName) throws NameNotFoundException {
        return packageManager.getPackageGids(packageName);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int[] getPackageGids(@NonNull String packageName, int flags) throws NameNotFoundException {
        return packageManager.getPackageGids(packageName,flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getPackageUid(@NonNull String packageName, int flags) throws NameNotFoundException {
        return packageManager.getPackageUid(packageName,flags);
    }

    @Override
    public PermissionInfo getPermissionInfo(@NonNull String permName, int flags) throws NameNotFoundException {
        return packageManager.getPermissionInfo(permName,flags);
    }

    @NonNull
    @Override
    public List<PermissionInfo> queryPermissionsByGroup(@Nullable String permissionGroup, int flags) throws NameNotFoundException {
        return packageManager.queryPermissionsByGroup(permissionGroup,flags);
    }

    @NonNull
    @Override
    public PermissionGroupInfo getPermissionGroupInfo(@NonNull String groupName, int flags) throws NameNotFoundException {
        return packageManager.getPermissionGroupInfo(groupName,flags);
    }

    @NonNull
    @Override
    public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
        return packageManager.getAllPermissionGroups(flags);
    }

    @NonNull
    @Override
    public ApplicationInfo getApplicationInfo(@NonNull String packageName, int flags) throws NameNotFoundException {
        return packageManager.getApplicationInfo(packageName,flags);
    }

    @NonNull
    @Override
    public ActivityInfo getActivityInfo(@NonNull ComponentName component, int flags) throws NameNotFoundException {
        return packageManager.getActivityInfo(component,flags);
    }

    @NonNull
    @Override
    public ActivityInfo getReceiverInfo(@NonNull ComponentName component, int flags) throws NameNotFoundException {
        return packageManager.getReceiverInfo(component,flags);
    }

    @NonNull
    @Override
    public ServiceInfo getServiceInfo(@NonNull ComponentName component, int flags) throws NameNotFoundException {
        return packageManager.getServiceInfo(component,flags);
    }

    @NonNull
    @Override
    public ProviderInfo getProviderInfo(@NonNull ComponentName component, int flags) throws NameNotFoundException {
        return packageManager.getProviderInfo(component,flags);
    }

    @NonNull
    @Override
    public List<PackageInfo> getInstalledPackages(int flags) {
        return packageManager.getInstalledPackages(flags);
    }

    @NonNull
    @Override
    public List<PackageInfo> getPackagesHoldingPermissions(@NonNull String[] permissions, int flags) {
        return packageManager.getPackagesHoldingPermissions(permissions,flags);
    }

    @Override
    public int checkPermission(@NonNull String permName, @NonNull String packageName) {
        return packageManager.checkPermission(permName,packageName);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean isPermissionRevokedByPolicy(@NonNull String permName, @NonNull String packageName) {
        return packageManager.isPermissionRevokedByPolicy(permName,packageName);
    }

    @Override
    public boolean addPermission(@NonNull PermissionInfo info) {
        return packageManager.addPermission(info);
    }

    @Override
    public boolean addPermissionAsync(@NonNull PermissionInfo info) {
        return packageManager.addPermissionAsync(info);
    }

    @Override
    public void removePermission(@NonNull String permName) {
        packageManager.removePermission(permName);
    }

    @Override
    public int checkSignatures(@NonNull String packageName1, @NonNull String packageName2) {
        return packageManager.checkSignatures(packageName1,packageName2);
    }

    @Override
    public int checkSignatures(int uid1, int uid2) {
        return packageManager.checkSignatures(uid1,uid2);
    }

    @Nullable
    @Override
    public String[] getPackagesForUid(int uid) {
        return packageManager.getPackagesForUid(uid);
    }

    @Nullable
    @Override
    public String getNameForUid(int uid) {
        return packageManager.getNameForUid(uid);
    }

    @NonNull
    @Override
    public List<ApplicationInfo> getInstalledApplications(int flags) {
        return packageManager.getInstalledApplications(flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean isInstantApp() {
        return packageManager.isInstantApp();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean isInstantApp(@NonNull String packageName) {
        return packageManager.isInstantApp(packageName);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int getInstantAppCookieMaxBytes() {
        return packageManager.getInstantAppCookieMaxBytes();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public byte[] getInstantAppCookie() {
        return packageManager.getInstantAppCookie();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void clearInstantAppCookie() {
        packageManager.clearInstantAppCookie();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void updateInstantAppCookie(@Nullable byte[] cookie) {
        packageManager.updateInstantAppCookie(cookie);
    }

    @Nullable
    @Override
    public String[] getSystemSharedLibraryNames() {
        return packageManager.getSystemSharedLibraryNames();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public List<SharedLibraryInfo> getSharedLibraries(int flags) {
        return packageManager.getSharedLibraries(flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public ChangedPackages getChangedPackages(int sequenceNumber) {
        return packageManager.getChangedPackages(sequenceNumber);
    }

    @NonNull
    @Override
    public FeatureInfo[] getSystemAvailableFeatures() {
        return packageManager.getSystemAvailableFeatures();
    }

    @Override
    public boolean hasSystemFeature(@NonNull String featureName) {
        return packageManager.hasSystemFeature(featureName);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean hasSystemFeature(@NonNull String featureName, int version) {
        return packageManager.hasSystemFeature(featureName,version);
    }

    @Nullable
    @Override
    public ResolveInfo resolveActivity(@NonNull Intent intent, int flags) {
        return packageManager.resolveActivity(intent,flags);
    }

    @NonNull
    @Override
    public List<ResolveInfo> queryIntentActivities(@NonNull Intent intent, int flags) {
        return packageManager.queryIntentActivities(intent,flags);
    }

    @NonNull
    @Override
    public List<ResolveInfo> queryIntentActivityOptions(@Nullable ComponentName caller, @Nullable Intent[] specifics, @NonNull Intent intent, int flags) {
        return packageManager.queryIntentActivityOptions(caller,specifics,intent,flags);
    }

    @NonNull
    @Override
    public List<ResolveInfo> queryBroadcastReceivers(@NonNull Intent intent, int flags) {
        return packageManager.queryBroadcastReceivers(intent,flags);
    }

    @Nullable
    @Override
    public ResolveInfo resolveService(@NonNull Intent intent, int flags) {
        return packageManager.resolveService(intent,flags);
    }

    @NonNull
    @Override
    public List<ResolveInfo> queryIntentServices(@NonNull Intent intent, int flags) {
        return packageManager.queryIntentServices(intent,flags);
    }

    @NonNull
    @Override
    public List<ResolveInfo> queryIntentContentProviders(@NonNull Intent intent, int flags) {
        return packageManager.queryIntentContentProviders(intent,flags);
    }

    @Nullable
    @Override
    public ProviderInfo resolveContentProvider(@NonNull String authority, int flags) {
        return packageManager.resolveContentProvider(authority,flags);
    }

    @NonNull
    @Override
    public List<ProviderInfo> queryContentProviders(@Nullable String processName, int uid, int flags) {
        return packageManager.queryContentProviders(processName,uid,flags);
    }

    @NonNull
    @Override
    public InstrumentationInfo getInstrumentationInfo(@NonNull ComponentName className, int flags) throws NameNotFoundException {
        return packageManager.getInstrumentationInfo(className,flags);
    }

    @NonNull
    @Override
    public List<InstrumentationInfo> queryInstrumentation(@NonNull String targetPackage, int flags) {
        return packageManager.queryInstrumentation(targetPackage,flags);
    }

    @Nullable
    @Override
    public Drawable getDrawable(@NonNull String packageName, int resid, @Nullable ApplicationInfo appInfo) {
        return packageManager.getDrawable(packageName,resid,appInfo);
    }

    @NonNull
    @Override
    public Drawable getActivityIcon(@NonNull ComponentName activityName) throws NameNotFoundException {
        return packageManager.getActivityIcon(activityName);
    }

    @NonNull
    @Override
    public Drawable getActivityIcon(@NonNull Intent intent) throws NameNotFoundException {
        return packageManager.getActivityIcon(intent);
    }

    @Nullable
    @Override
    public Drawable getActivityBanner(@NonNull ComponentName activityName) throws NameNotFoundException {
        return packageManager.getActivityBanner(activityName);
    }

    @Nullable
    @Override
    public Drawable getActivityBanner(@NonNull Intent intent) throws NameNotFoundException {
        return packageManager.getActivityBanner(intent);
    }

    @NonNull
    @Override
    public Drawable getDefaultActivityIcon() {
        return packageManager.getDefaultActivityIcon();
    }

    @NonNull
    @Override
    public Drawable getApplicationIcon(@NonNull ApplicationInfo info) {
        return packageManager.getApplicationIcon(info);
    }

    @NonNull
    @Override
    public Drawable getApplicationIcon(@NonNull String packageName) throws NameNotFoundException {
        return packageManager.getApplicationIcon(packageName);
    }

    @Nullable
    @Override
    public Drawable getApplicationBanner(@NonNull ApplicationInfo info) {
        return packageManager.getApplicationBanner(info);
    }

    @Nullable
    @Override
    public Drawable getApplicationBanner(@NonNull String packageName) throws NameNotFoundException {
        return packageManager.getApplicationBanner(packageName);
    }

    @Nullable
    @Override
    public Drawable getActivityLogo(@NonNull ComponentName activityName) throws NameNotFoundException {
        return packageManager.getActivityLogo(activityName);
    }

    @Nullable
    @Override
    public Drawable getActivityLogo(@NonNull Intent intent) throws NameNotFoundException {
        return packageManager.getActivityLogo(intent);
    }

    @Nullable
    @Override
    public Drawable getApplicationLogo(@NonNull ApplicationInfo info) {
        return packageManager.getApplicationLogo(info);
    }

    @Nullable
    @Override
    public Drawable getApplicationLogo(@NonNull String packageName) throws NameNotFoundException {
        return packageManager.getApplicationLogo(packageName);
    }

    @NonNull
    @Override
    public Drawable getUserBadgedIcon(@NonNull Drawable drawable, @NonNull UserHandle user) {
        return packageManager.getUserBadgedIcon(drawable,user);
    }

    @NonNull
    @Override
    public Drawable getUserBadgedDrawableForDensity(@NonNull Drawable drawable, @NonNull UserHandle user, @Nullable Rect badgeLocation, int badgeDensity) {
        return packageManager.getUserBadgedDrawableForDensity(drawable,user,badgeLocation,badgeDensity);
    }

    @NonNull
    @Override
    public CharSequence getUserBadgedLabel(@NonNull CharSequence label, @NonNull UserHandle user) {
        return packageManager.getUserBadgedLabel(label,user);
    }

    @Nullable
    @Override
    public CharSequence getText(@NonNull String packageName, int resid, @Nullable ApplicationInfo appInfo) {
        return packageManager.getText(packageName,resid,appInfo);
    }

    @Nullable
    @Override
    public XmlResourceParser getXml(@NonNull String packageName, int resid, @Nullable ApplicationInfo appInfo) {
        return packageManager.getXml(packageName,resid,appInfo);
    }

    @NonNull
    @Override
    public CharSequence getApplicationLabel(@NonNull ApplicationInfo info) {
        return packageManager.getApplicationLabel(info);
    }

    @NonNull
    @Override
    public Resources getResourcesForActivity(@NonNull ComponentName activityName) throws NameNotFoundException {
        return packageManager.getResourcesForActivity(activityName);
    }

    @NonNull
    @Override
    public Resources getResourcesForApplication(@NonNull ApplicationInfo app) throws NameNotFoundException {
        return packageManager.getResourcesForApplication(app);
    }

    @NonNull
    @Override
    public Resources getResourcesForApplication(@NonNull String packageName) throws NameNotFoundException {
        return packageManager.getResourcesForApplication(packageName);
    }

    @Override
    public void verifyPendingInstall(int id, int verificationCode) {
        packageManager.verifyPendingInstall(id,verificationCode);
    }

    @Override
    public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) {
        packageManager.extendVerificationTimeout(id,verificationCodeAtTimeout,millisecondsToDelay);
    }

    @Override
    public void setInstallerPackageName(@NonNull String targetPackage, @Nullable String installerPackageName) {
        packageManager.setInstallerPackageName(targetPackage,installerPackageName);
    }

    @Nullable
    @Override
    public String getInstallerPackageName(@NonNull String packageName) {
        return packageManager.getInstallerPackageName(packageName);
    }

    @Override
    public void addPackageToPreferred(@NonNull String packageName) {
        packageManager.addPackageToPreferred(packageName);
    }

    @Override
    public void removePackageFromPreferred(@NonNull String packageName) {
        packageManager.removePackageFromPreferred(packageName);
    }

    @NonNull
    @Override
    public List<PackageInfo> getPreferredPackages(int flags) {
        return packageManager.getPreferredPackages(flags);
    }

    @Override
    public void addPreferredActivity(@NonNull IntentFilter filter, int match, @Nullable ComponentName[] set, @NonNull ComponentName activity) {
        packageManager.addPreferredActivity(filter,match,set,activity);
    }

    @Override
    public void clearPackagePreferredActivities(@NonNull String packageName) {
        packageManager.clearPackagePreferredActivities(packageName);
    }

    @Override
    public int getPreferredActivities(@NonNull List<IntentFilter> outFilters, @NonNull List<ComponentName> outActivities, @Nullable String packageName) {
        return packageManager.getPreferredActivities(outFilters,outActivities,packageName);
    }

    @Override
    public void setComponentEnabledSetting(@NonNull ComponentName componentName, int newState, int flags) {
        packageManager.setComponentEnabledSetting(componentName,newState,flags);
    }

    @Override
    public int getComponentEnabledSetting(@NonNull ComponentName componentName) {
        return packageManager.getComponentEnabledSetting(componentName);
    }

    @Override
    public void setApplicationEnabledSetting(@NonNull String packageName, int newState, int flags) {
        packageManager.setApplicationEnabledSetting(packageName,newState,flags);
    }

    @Override
    public int getApplicationEnabledSetting(@NonNull String packageName) {
        return packageManager.getApplicationEnabledSetting(packageName);
    }

    @Override
    public boolean isSafeMode() {
        return packageManager.isSafeMode();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setApplicationCategoryHint(@NonNull String packageName, int categoryHint) {
        packageManager.setApplicationCategoryHint(packageName,categoryHint);
    }

    @NonNull
    @Override
    public PackageInstaller getPackageInstaller() {
        return packageManager.getPackageInstaller();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean canRequestPackageInstalls() {
        return packageManager.canRequestPackageInstalls();
    }
}
