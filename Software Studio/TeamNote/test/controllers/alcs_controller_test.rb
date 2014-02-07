require 'test_helper'

class AlcsControllerTest < ActionController::TestCase
  setup do
    @alc = alcs(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:alcs)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create alc" do
    assert_difference('Alc.count') do
      post :create, alc: { new: @alc.new }
    end

    assert_redirected_to alc_path(assigns(:alc))
  end

  test "should show alc" do
    get :show, id: @alc
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @alc
    assert_response :success
  end

  test "should update alc" do
    patch :update, id: @alc, alc: { new: @alc.new }
    assert_redirected_to alc_path(assigns(:alc))
  end

  test "should destroy alc" do
    assert_difference('Alc.count', -1) do
      delete :destroy, id: @alc
    end

    assert_redirected_to alcs_path
  end
end
