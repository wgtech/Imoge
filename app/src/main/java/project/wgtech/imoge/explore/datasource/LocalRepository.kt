package project.wgtech.imoge.explore.datasource

import project.wgtech.imoge.R
import project.wgtech.imoge.util.ResourceProviderImpl

class LocalRepository {

    fun getChips(provider : ResourceProviderImpl) = provider.stringArray(R.array.test_array).toMutableList()
}