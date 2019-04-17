package tv.sporttotal.makers


///**
// * A coroutine dispatcher that is not confined to any specific thread.
// * It executes initial continuation of the coroutine _immediately_ in the current call-frame
// * and lets the coroutine resume in whatever thread that is used by the corresponding suspending function, without
// * mandating any specific threading policy.
// * **Note: use with extreme caution, not for general code**.
// *
// * Note, that if you need your coroutine to be confined to a particular thread or a thread-pool after resumption,
// * but still want to execute it in the current call-frame until its first suspension, then you can use
// * an optional [CoroutineStart] parameter in coroutine builders like
// * [launch][CoroutineScope.launch] and [async][CoroutineScope.async] setting it to the
// * the value of [CoroutineStart.UNDISPATCHED].
// *
// * **Note: This is an experimental api.**
// * Semantics, order of execution, and particular implementation details of this dispatcher may change in the future.
// */
//@JvmStatic
//@ExperimentalCoroutinesApi
//public actual val Unconfined: CoroutineDispatcher = kotlinx.coroutines.Unconfined
//


//
//big issue: cant swap out dispatcher!
//https://github.com/Kotlin/kotlinx.coroutines/issues/810

//solved via:
//kotlinx-coroutines-debug
//https://github.com/Kotlin/kotlinx.coroutines/pull/749
// since 1.1
//
//@Before
//fun setUp() {
//    Dispatchers.setMain(mainThreadSurrogate)
//}
//@After
//fun tearDown() {
//    Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher


//Integrate TestCoroutineContext with structured concurrency
//https://github.com/Kotlin/kotlinx.coroutines/issues/541
//
//Slit up TestCoroutineContext into TestCoroutineDispatcher, TestCoroutineExceptionHandler and TestCoroutineScope
//Added DelayController interface for testing libraries to expose Dispatcher control
//Added ExceptionCaptor inerface for testing libraries to expose uncaught exceptions
//Added builder for testing coroutines runBlockingTest
//Removed old builder withTestContext
