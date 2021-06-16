package com.example.projectdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdemo.adapter.AccountAdapter
import com.example.projectdemo.models.AccountModel
import com.example.projectdemo.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val mAdapterAccount = AccountAdapter()
    private val mListAccount = arrayListOf<AccountModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSearchByName.setOnClickListener {
            getUsersByUserName(edtSearch.text.toString())
        }
        btnSearchByParameter.setOnClickListener {
            getUserByParameter(
                edtPhone.text.toString(),
                edtEmail.text.toString(),
                edtUserName.text.toString(),
                edtFirstName.text.toString(),
                edtLastName.text.toString()
            )
        }
        btnAdd.setOnClickListener {
            createAccount(
                AccountModel(
                    edtEmail.text.toString(),
                    edtFirstName.text.toString(),
                    edtLastName.text.toString(),
                    edtPassword.text.toString(),
                    edtPhone.text.toString(),
                    "new",
                    edtUserName.text.toString()
                )
            )
        }
        btnUpdate.setOnClickListener {
            updateAccount(
                edtUserName.text.toString(), AccountModel(
                    edtEmail.text.toString(),
                    edtFirstName.text.toString(),
                    edtLastName.text.toString(),
                    edtPassword.text.toString(),
                    edtPhone.text.toString(),
                    "new",
                    edtUserName.text.toString(),
                    edtId.text.toString().toInt()
                )
            )
        }
        btnRemove.setOnClickListener {
            deleteAccount(edtUserName.text.toString())
        }
        setupRecyclerView()
    }

    private fun getUserByParameter(
        phone: String,
        email: String,
        username: String,
        firstname: String,
        lastname: String
    ) {
        RetrofitClient().getService()
            .searchUserByParameter("new", phone, email, username, firstname, lastname)
            .enqueue(object : Callback<ArrayList<AccountModel>> {
                override fun onResponse(
                    call: Call<ArrayList<AccountModel>>,
                    response: Response<ArrayList<AccountModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            mListAccount.clear()
                            mListAccount.addAll(it)
                            setupRecyclerView()
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<AccountModel>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Invalid query params", Toast.LENGTH_LONG)
                        .show()
                }

            })
    }

    private fun deleteAccount(username: String) {
        RetrofitClient().getService().deleteUser(username)
            .enqueue(object : Callback<AccountModel> {
                override fun onResponse(
                    call: Call<AccountModel>,
                    response: Response<AccountModel>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Remove successful", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<AccountModel>, t: Throwable) {

                }

            })
    }

    private fun updateAccount(username: String, accountModel: AccountModel) {
        RetrofitClient().getService().updateUser(username, accountModel)
            .enqueue(object : Callback<AccountModel> {

                override fun onResponse(
                    call: Call<AccountModel>,
                    response: Response<AccountModel>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Update successful", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<AccountModel>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Update false", Toast.LENGTH_LONG).show()
                }


            })
    }

    private fun createAccount(
        account: AccountModel
    ) {
        RetrofitClient().getService().createUser(account)
            .enqueue(object : Callback<AccountModel> {
                override fun onResponse(
                    call: Call<AccountModel>,
                    response: Response<AccountModel>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Create successful", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<AccountModel>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "error: ${t.message}", Toast.LENGTH_LONG)
                        .show()
                }

            })
    }


    private fun setupRecyclerView() {
        rvAccount.apply {
            adapter = mAdapterAccount
            layoutManager = LinearLayoutManager(context)
        }
        mAdapterAccount.setData(mListAccount)
    }

    private fun getUsersByUserName(userName: String) {
        RetrofitClient().getService().getUsersByUserName(userName)
            .enqueue(object : Callback<AccountModel> {
                override fun onResponse(
                    call: Call<AccountModel>,
                    response: Response<AccountModel>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            mListAccount.clear()
                            mListAccount.add(it)
                            setupRecyclerView()
                        }
                    }
                }

                override fun onFailure(call: Call<AccountModel>, t: Throwable) {
                    Log.d(TAG_HTTP, "error: ${t.message.toString()}")
                }

            })
    }

    companion object {
        private const val TAG_HTTP = "TAG_HTTP"
    }
}