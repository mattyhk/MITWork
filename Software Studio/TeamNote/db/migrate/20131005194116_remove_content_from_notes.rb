class RemoveContentFromNotes < ActiveRecord::Migration
  def change
    remove_column :notes, :content, :string
  end
end
