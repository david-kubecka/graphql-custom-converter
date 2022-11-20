# Testing

Run the `GraphqlCustomConverterApplicationTests`.

Currently it fails on "No primary or single unique constructor found for interface javax.money.MonetaryAmount" because the custom converter is probably misconfigured. The goal is to modify the code so that the input Money/MonetaryAmount type can be successfully deserialized.
