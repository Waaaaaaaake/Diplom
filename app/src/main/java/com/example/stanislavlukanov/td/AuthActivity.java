package com.example.stanislavlukanov.td;

import android.support.v4.app.Fragment;

public class AuthActivity extends SingleFragmentActivity {

    // Далее возврат метода из класса AuthFragment

    @Override
    protected Fragment getFragment() {
        return AuthFragment.newInstance();
    }

}
