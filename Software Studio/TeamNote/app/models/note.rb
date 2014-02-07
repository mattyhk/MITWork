# A Note must have an author 

class Note < ActiveRecord::Base
	validates :user_id, presence: true
	
	has_many :alcs, dependent: :destroy
	has_many :users, through: :alcs

	accepts_nested_attributes_for :alcs, :reject_if => lambda { |a| a[:user_id].blank? }, 
		:allow_destroy => true
end
