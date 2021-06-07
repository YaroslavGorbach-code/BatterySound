package koropapps.yaroslavgorbach.batterysound.data

interface RepoProvider {
    fun provideRepo(): RepoImp
}