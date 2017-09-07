**View 的invalidate() 方法**

* invalidateCache 参数

此view的绘图缓存是否应该失效? true：完全失效  false:如果view的内容或尺寸不变，则可以不需要缓存失效
大多数情况下，view 的 invalidate() 方法都是彻底刷新的，不保留缓存。
有一种情况是 false: invalidateViewProperty() 可能会出现调用

* invalidate() 作用
会使得View 重新绘制
具体：会让其 当前View的父节点View 来刷新子节点绘制，过程中会干掉脏数据的矩形Rect，并作出新的矩形。
invalidateChild()，invalidateChildInParent()对脏矩形做位置变换

invalidate() -> draw() -> onDraw()

```java
/**
 * This is where the invalidate() work actually happens. A full invalidate()
 * causes the drawing cache to be invalidated, but this function can be
 * called with invalidateCache set to false to skip that invalidation step
 * for cases that do not need it (for example, a component that remains at
 * the same dimensions with the same content).
 *
 * @param invalidateCache Whether the drawing cache for this view should be
 *            invalidated as well. This is usually true for a full
 *            invalidate, but may be set to false if the View's contents or
 *            dimensions have not changed.
 */
void invalidate(boolean invalidateCache) {
    invalidateInternal(0, 0, mRight - mLeft, mBottom - mTop, invalidateCache, true);
}

void invalidateInternal(int l, int t, int r, int b, boolean invalidateCache,
        boolean fullInvalidate) {
    if (mGhostView != null) {
        mGhostView.invalidate(true);
        return;
    }

    if (skipInvalidate()) {
        return;
    }

    if ((mPrivateFlags & (PFLAG_DRAWN | PFLAG_HAS_BOUNDS)) == (PFLAG_DRAWN | PFLAG_HAS_BOUNDS)
            || (invalidateCache && (mPrivateFlags & PFLAG_DRAWING_CACHE_VALID) == PFLAG_DRAWING_CACHE_VALID)
            || (mPrivateFlags & PFLAG_INVALIDATED) != PFLAG_INVALIDATED
            || (fullInvalidate && isOpaque() != mLastIsOpaque)) {
        if (fullInvalidate) {
            mLastIsOpaque = isOpaque();
            mPrivateFlags &= ~PFLAG_DRAWN;
        }

        mPrivateFlags |= PFLAG_DIRTY;

        if (invalidateCache) {
            mPrivateFlags |= PFLAG_INVALIDATED;
            mPrivateFlags &= ~PFLAG_DRAWING_CACHE_VALID;
        }

        // Propagate the damage rectangle to the parent view.
        final AttachInfo ai = mAttachInfo;
        final ViewParent p = mParent;
        if (p != null && ai != null && l < r && t < b) {
            final Rect damage = ai.mTmpInvalRect;
            damage.set(l, t, r, b);
            p.invalidateChild(this, damage);
        }

        // Damage the entire projection receiver, if necessary.
        if (mBackground != null && mBackground.isProjected()) {
            final View receiver = getProjectionReceiver();
            if (receiver != null) {
                receiver.damageInParent();
            }
        }

        // Damage the entire IsolatedZVolume receiving this view's shadow.
        if (isHardwareAccelerated() && getZ() != 0) {
            damageShadowReceiver();
        }
    }
}
```