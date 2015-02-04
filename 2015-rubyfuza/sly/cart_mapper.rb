class CartMapper < BaseMapper
  attributes :id, :expires_at, :total, :item_count

  link :self, '/api/cart'

  has_many :line_items, collection_mapper: LineItemCollectionMapper
  has_one :promotion_code

  form :empty_cart do
    method  'DELETE'
    action '/api/cart'
  end

  form :promotion_code do
    method 'POST'
    action '/api/cart/promotion_code'

    text :code,
         label: 'promotion_code.label',
         value: ->{ cart.promotion_code.code }
  end
