class AddContentToNotes < ActiveRecord::Migration
  def change
    add_column :notes, :content, :text
  end
end
