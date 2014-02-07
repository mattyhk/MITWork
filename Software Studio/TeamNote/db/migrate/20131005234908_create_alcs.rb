class CreateAlcs < ActiveRecord::Migration
  def change
    create_table :alcs do |t|
      t.integer :user_id
      t.integer :note_id
      t.boolean :read
      t.boolean :write

      t.timestamps
    end
  end
end
