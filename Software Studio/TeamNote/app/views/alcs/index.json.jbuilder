json.array!(@alcs) do |alc|
  json.extract! alc, :new
  json.url alc_url(alc, format: :json)
end
