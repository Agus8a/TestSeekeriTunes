package com.example.domain.core

import java.io.IOException

class BaseException(val error: BaseError) : IOException()